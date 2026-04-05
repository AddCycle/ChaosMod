package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import util.Reference;

public class ModLootTableList {
	private static final List<ResourceLocation> LOOT_TABLES = new ArrayList<>();

	public static final ResourceLocation CHEST_VILLAGE_BLACKSMITH = register("dungeon_loot");
	public static final ResourceLocation CUSTOM_FISHING = register("inject/gameplay/custom_fishing");
	
	public static void registerLootTables() {
		for (ResourceLocation rl : LOOT_TABLES) {
			LootTableList.register(rl);
		}
	}

	private static ResourceLocation register(String id) {
		ResourceLocation rl = new ResourceLocation(Reference.MODID, id);
		LOOT_TABLES.add(rl);
		return rl;
	}
}
