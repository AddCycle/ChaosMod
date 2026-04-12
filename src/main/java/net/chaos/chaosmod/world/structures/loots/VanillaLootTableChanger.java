package net.chaos.chaosmod.world.structures.loots;

import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class VanillaLootTableChanger {
	
	@SubscribeEvent
	public static void onLootTableLoad(LootTableLoadEvent event) {
		if (event.getName().toString().equals("minecraft:chests/village_blacksmith")) {
			System.out.println("Village blacksmith lootTable loaded");
	    }
	}
}