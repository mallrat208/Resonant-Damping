package com.mr208.resdamp.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.mr208.resdamp.ResonantDamping;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(modid = ResonantDamping.MOD_ID, value = Side.CLIENT)
public class ClientEventHandler
{
	public static ClientEventHandler INSTANCE = new ClientEventHandler();
	
	public ClientEventHandler()
	{
	
	}
	
	@SubscribeEvent
	public static void onToolTip(ItemTooltipEvent event)
	{
		if(ResonantDamping.isAntiRadiation(event.getItemStack()))
		{
			event.getToolTip().add(ChatFormatting.RED + I18n.format("resdamp.tooltip.installed"));
		}
	}
}
