package net.chaos.chaosmod.world.structures.loots;

import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@Mod.EventBusSubscriber(modid = Reference.MODID, value = Side.SERVER)
public class VanillaLootTableChanger {
	
	@SubscribeEvent
	public void onLootTableLoad(LootTableLoadEvent event) {
		if (event.getName().toString().equals("minecraft:chests/village_blacksmith")) {
			System.out.println("Village blacksmith loottable loaded");
	    }
	}
}
