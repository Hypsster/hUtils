package com.utils.gauntletFlicker;

import com.utils.HypsApiPlugin.HypsApiPlugin;
import com.utils.HypsApiPlugin.QuickPrayer;
import com.utils.InteractionApi.InteractionHelper;
import com.utils.PacketUtilsPlugin;
import com.utils.Packets.MousePackets;
import com.utils.Packets.WidgetPackets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.GameState;
import net.runelite.api.HeadIcon;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.ItemComposition;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.Skill;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.util.Set;

@PluginDescriptor(
		name = "Gauntlet Flicker and Switcher",
		description = "",
		tags = {"Hyps"}
)
@PluginDependency(HypsApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)
@Slf4j
public class gauntletFlicker extends Plugin
{
	@Inject
	Client client;
	@Inject
	ItemManager manager;
	String updatedWeapon = "";
	boolean forceTab = false;
	Set<Integer> HUNLLEF_IDS = Set.of(NpcID.CORRUPTED_HUNLLEF, NpcID.CORRUPTED_HUNLLEF_9036,
			NpcID.CORRUPTED_HUNLLEF_9037, NpcID.CORRUPTED_HUNLLEF_9038, NpcID.CRYSTALLINE_HUNLLEF,
			NpcID.CRYSTALLINE_HUNLLEF_9022, NpcID.CRYSTALLINE_HUNLLEF_9023, NpcID.CRYSTALLINE_HUNLLEF_9024);
	boolean isRange = true;


	@Subscribe
	public void onGameTick(GameTick e)
	{
		if (client.getLocalPlayer().isDead() || client.getLocalPlayer().getHealthRatio() == 0)
		{
			forceTab = false;
			return;
		}
		if (client.getGameState() != GameState.LOGGED_IN)
		{
			forceTab = false;
			return;
		}
		Item weapon = null;
		try
		{
			weapon = client.getItemContainer(InventoryID.EQUIPMENT).getItem(EquipmentInventorySlot.WEAPON.getSlotIdx());
		}
		catch (NullPointerException ex)
		{
			//todo
		}
		String name = "";
		if (weapon != null)
		{
			ItemComposition itemComp =
					manager.getItemComposition(client.getItemContainer(InventoryID.EQUIPMENT).getItem(EquipmentInventorySlot.WEAPON.getSlotIdx()).getId());
			name = itemComp.getName() == null ? "" : itemComp.getName();
		}
		NPC hunllef = client.getNpcs().stream().filter(x -> HUNLLEF_IDS.contains(x.getId())).findFirst().orElse(null);
		if (client.getVarbitValue(9177) != 1)
		{
			forceTab = false;
			return;
		}
		if (client.getVarbitValue(9178) != 1)
		{
			isRange = true;
			forceTab = false;
			updatedWeapon = "";
			return;
		}
		if (forceTab)
		{
			client.runScript(915, 3);
			forceTab = false;
		}
		if (client.getWidget(5046276) == null)
		{
			MousePackets.queueClickPacket();
			WidgetPackets.queueWidgetAction(client.getWidget(WidgetInfo.MINIMAP_QUICK_PRAYER_ORB), "Setup");
			forceTab = true;
		}
		if (isRange && !HypsApiPlugin.isQuickPrayerActive(QuickPrayer.PROTECT_FROM_MISSILES))
		{
			MousePackets.queueClickPacket();
			WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, 13); //quickPrayer range
		}
		else if (!isRange && !HypsApiPlugin.isQuickPrayerActive(QuickPrayer.PROTECT_FROM_MAGIC))
		{
			MousePackets.queueClickPacket();
			WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, 12); //quickPrayer magic
		}
		if (hunllef != null)
		{
			if (HypsApiPlugin.getHeadIcon(hunllef) == HeadIcon.MAGIC && (!name.contains("bow") && !name.contains("halberd")))
			{
				Widget bow = HypsApiPlugin.getItem("*bow*");
				Widget halberd = HypsApiPlugin.getItem("*halberd*");
				if (bow != null)
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetAction(bow, "Wield");
					updatedWeapon = "bow";
				}
				else if (halberd != null)
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetAction(halberd, "Wield");
					updatedWeapon = "halberd";
				}
			}
			if (HypsApiPlugin.getHeadIcon(hunllef) == HeadIcon.RANGED && (!name.contains("staff") && !name.contains("halberd")))
			{
				Widget staff = HypsApiPlugin.getItem("*staff*");
				Widget halberd = HypsApiPlugin.getItem("*halberd*");
				if (staff != null)
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetAction(staff, "Wield");
					updatedWeapon = "staff";
				}
				else if (halberd != null)
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetAction(halberd, "Wield");
					updatedWeapon = "halberd";
				}
			}
			if (HypsApiPlugin.getHeadIcon(hunllef) == HeadIcon.MELEE && (!name.contains("staff") && !name.contains("bow")))
			{
				Widget staff = HypsApiPlugin.getItem("*staff*");
				Widget bow = HypsApiPlugin.getItem("*bow*");
				if (staff != null)
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetAction(staff, "Wield");
					updatedWeapon = "staff";
				}
				else if (bow != null)
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetAction(bow, "Wield");
					updatedWeapon = "bow";
				}
			}
			String weaponTesting = updatedWeapon.isEmpty() ? name : updatedWeapon;
			if (weaponTesting.contains("bow"))
			{
				if (rigourUnlocked() && !HypsApiPlugin.isQuickPrayerActive(QuickPrayer.RIGOUR))
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, 24); //quickPrayer rigour
				}
				else if (!rigourUnlocked() && !HypsApiPlugin.isQuickPrayerActive(QuickPrayer.EAGLE_EYE))
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, 22); //quickPrayer eagle eye
				}
			}
			else if (weaponTesting.contains("staff"))
			{
				if (auguryUnlucked() && !HypsApiPlugin.isQuickPrayerActive(QuickPrayer.AUGURY))
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, 27); //quickPrayer augury
				}
				else if (!auguryUnlucked() && !HypsApiPlugin.isQuickPrayerActive(QuickPrayer.MYSTIC_MIGHT))
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, 23); //quickPrayer mystic might
				}
			}
			else if (weaponTesting.contains("halberd"))
			{
				if (pietyUnlocked() && !HypsApiPlugin.isQuickPrayerActive(QuickPrayer.PIETY))
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, 26);
				}
				else if (!pietyUnlocked() && !HypsApiPlugin.isQuickPrayerActive(QuickPrayer.ULTIMATE_STRENGTH))
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, 10);
					if (!HypsApiPlugin.isQuickPrayerActive(QuickPrayer.INCREDIBLE_REFLEXES))
					{
						MousePackets.queueClickPacket();
						WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, 11);
					}
				}else if(!pietyUnlocked() && !HypsApiPlugin.isQuickPrayerActive(QuickPrayer.STEEL_SKIN))
				{
					MousePackets.queueClickPacket();
					WidgetPackets.queueWidgetActionPacket(1, 5046276, -1, QuickPrayer.STEEL_SKIN.getIndex());
				}
			}
		}
		if (HypsApiPlugin.isQuickPrayerEnabled())
		{
			InteractionHelper.togglePrayer();
		}
		InteractionHelper.togglePrayer();
	}

	public boolean rigourUnlocked()
	{
		return !(client.getVarbitValue(5451) == 0)&&client.getRealSkillLevel(Skill.PRAYER)>=74&&client.getRealSkillLevel(Skill.DEFENCE)>=70;
	}

	public boolean pietyUnlocked()
	{
		return client.getVarbitValue(3909) == 8&&client.getRealSkillLevel(Skill.PRAYER)>=70&&client.getRealSkillLevel(Skill.DEFENCE)>=70;
	}

	public boolean auguryUnlucked()
	{
		return !(client.getVarbitValue(5452) == 0)&&client.getRealSkillLevel(Skill.PRAYER)>=77&&client.getRealSkillLevel(Skill.DEFENCE)>=70;
	}


	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		if (event.getMenuOption().toLowerCase().contains("pass"))
		{
			isRange = true;
		}
	}

	@Override
	protected void startUp()
	{
		isRange = true;
		forceTab = false;
		updatedWeapon = "";
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged e)
	{
		if (e.getActor() == null)
		{
			return;
		}
		if (!(e.getActor() instanceof NPC))
		{
			return;
		}

		NPC npc = (NPC) e.getActor();
		if (!HUNLLEF_IDS.contains(npc.getId()))
		{
			return;
		}
		if (e.getActor().getAnimation() == 8754)
		{
			isRange = false;
		}
		if (e.getActor().getAnimation() == 8755)
		{
			isRange = true;
		}
	}

	private boolean isHunllefVarbitSet()
	{
		return client.getVarbitValue(9177) == 1;
	}
}