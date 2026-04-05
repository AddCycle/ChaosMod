package net.chaos.chaosmod.common.events;

import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class CommonEvents {
	
	@SubscribeEvent
	public static void onCommandExecuted(CommandEvent event) {}
}