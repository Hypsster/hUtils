package com.utils.TestPlugin;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.utils.HypsApiPlugin.HypsApiPlugin;
import com.utils.HypsApiPlugin.Inventory;
import com.utils.InteractionApi.InventoryInteraction;
import com.utils.PacketUtilsPlugin;
import com.utils.Packets.MousePackets;
import com.utils.Packets.WidgetPackets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.ItemManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import com.utils.Packets.WidgetPackets;
import static com.utils.PacketReflection.client;

@PluginDescriptor(
		name = "Test Plugin",
		enabledByDefault = false,
		tags = {"Hyps"},
		description = "Testing shit out by Hyps"
)

@PluginDependency(HypsApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)
@Slf4j
public class Tester extends Plugin
{
	@Inject
	Client clinet;

	@Subscribe
	public void onGameTick(GameTick event)
	{
		int hp = client.getBoostedSkillLevel(Skill.HITPOINTS);


		if (hp > 10)
		{
			drop();
			clinet.addChatMessage(ChatMessageType.GAMEMESSAGE,"","Eating Food", null);
		}
	}

	private void drop()
	{
		Optional<Widget> fish = Inventory.search().idInList(List.of(371, 13441)).first();

		if (fish.isPresent())
		{
			WidgetPackets.queueWidgetAction(fish.get(), "Eat");
		}
	}
}