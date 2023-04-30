package com.example;

import com.utils.AutoTele.AutoTele;
import com.utils.HypsApiPlugin.HypsApiPlugin;
import com.utils.PacketUtilsPlugin;
import com.utils.gauntletFlicker.gauntletFlicker;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ExamplePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(HypsApiPlugin.class, PacketUtilsPlugin.class,
				gauntletFlicker.class, AutoTele.class);
		RuneLite.main(args);
	}
}