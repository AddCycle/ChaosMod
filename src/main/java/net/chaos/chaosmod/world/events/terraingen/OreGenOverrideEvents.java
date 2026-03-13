package net.chaos.chaosmod.world.events.terraingen;

import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable;
import net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OreGenOverrideEvents {

	@SubscribeEvent
	public void onOreGen(GenerateMinable event) {
		if (event.getWorld().isRemote) return;

		if (verifyGarbageBlocks(event.getType())) {
			event.setResult(Result.DENY);
		}
	}
	
	private boolean verifyGarbageBlocks(EventType type) {
		return type == EventType.ANDESITE || type == EventType.GRANITE || type == EventType.DIORITE;
	}
}