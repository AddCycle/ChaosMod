package net.chaos.chaosmod.world.events.terraingen;

import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OreGenOverrideEvents {

	@SubscribeEvent
	public void onOreGen(GenerateMinable event) {
		if (event.getWorld().isRemote) return;
	}
}