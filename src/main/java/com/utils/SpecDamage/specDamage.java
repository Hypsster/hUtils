package com.utils.SpecDamage;

import com.google.inject.Inject;
import com.utils.HypsApiPlugin.HypsApiPlugin;
import com.utils.HypsApiPlugin.Inventory;
import com.utils.PacketUtilsPlugin;
import com.utils.Packets.WidgetPackets;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDependency;
import net.runelite.client.plugins.PluginDescriptor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.utils.PacketReflection.client;

@PluginDescriptor(
		name = "Spec Damage Tracker",
		enabledByDefault = false,
		tags = {"Hyps"},
		description = "Testing shit out by Hyps"
)

@PluginDependency(HypsApiPlugin.class)
@PluginDependency(PacketUtilsPlugin.class)
@Slf4j
public class specDamage extends Plugin
{
	@Inject
	Client clinet;

	@Inject
	NPC npc;

	Set<Integer> NPC_IDS = Set.of(NpcID.AVIANSIE_3171); // NPC ID's HERE


	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (NPC_IDS.contains(NpcID.AVIANSIE_3171))
		{
			//MAX BGS
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