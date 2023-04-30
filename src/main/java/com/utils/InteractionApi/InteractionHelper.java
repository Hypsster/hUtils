package com.utils.InteractionApi;

import com.utils.Packets.MousePackets;
import com.utils.Packets.WidgetPackets;
import net.runelite.api.widgets.WidgetInfo;

import java.util.List;

public class InteractionHelper
{
	static private int quickPrayerWidgetID = WidgetInfo.MINIMAP_QUICK_PRAYER_ORB.getPackedId();

	public static void toggleNormalPrayer(int packedWidgID)
	{
		MousePackets.queueClickPacket();
		WidgetPackets.queueWidgetActionPacket(1, packedWidgID, -1, -1);
	}


	public static void toggleNormalPrayers(List<Integer> packedWidgIDs)
	{
		for (Integer packedWidgID : packedWidgIDs)
		{
			MousePackets.queueClickPacket();
			WidgetPackets.queueWidgetActionPacket(1, packedWidgID, -1, -1);
		}
	}

	public static void togglePrayer()
	{
		MousePackets.queueClickPacket(0, 0);
		WidgetPackets.queueWidgetActionPacket(1, quickPrayerWidgetID, -1, -1);
	}
}
