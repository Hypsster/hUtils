package com.utils.InteractionApi;

import com.utils.HypsApiPlugin.BankInventory;
import com.utils.Packets.MousePackets;
import com.utils.Packets.WidgetPackets;
import net.runelite.api.widgets.Widget;

import java.util.Optional;
import java.util.function.Predicate;

public class BankInventoryInteraction
{
	public static boolean useItem(String name, String... actions)
	{
		return BankInventory.search().withName(name).first().flatMap(item ->
		{
			MousePackets.queueClickPacket();
			WidgetPackets.queueWidgetAction(item, actions);
			return Optional.of(true);
		}).orElse(false);
	}

	public static boolean useItem(int id, String... actions)
	{
		return BankInventory.search().withId(id).first().flatMap(item ->
		{
			MousePackets.queueClickPacket();
			WidgetPackets.queueWidgetAction(item, actions);
			return Optional.of(true);
		}).orElse(false);
	}

	public static boolean useItem(Predicate<? super Widget> predicate, String... actions)
	{
		return BankInventory.search().filter(predicate).first().flatMap(item ->
		{
			MousePackets.queueClickPacket();
			WidgetPackets.queueWidgetAction(item, actions);
			return Optional.of(true);
		}).orElse(false);
	}

	public static boolean useItemIndex(int index, String... actions)
	{
		return BankInventory.search().indexIs(index).first().flatMap(item ->
		{
			MousePackets.queueClickPacket();
			WidgetPackets.queueWidgetAction(item, actions);
			return Optional.of(true);
		}).orElse(false);
	}

	public static boolean useItem(Widget item, String... actions)
	{
		if (item == null)
		{
			return false;
		}
		MousePackets.queueClickPacket();
		WidgetPackets.queueWidgetAction(item, actions);
		return true;
	}
}
