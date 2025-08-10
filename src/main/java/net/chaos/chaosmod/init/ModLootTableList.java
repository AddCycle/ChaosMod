package net.chaos.chaosmod.init;

import net.minecraft.util.ResourceLocation;
import util.Reference;

public class ModLootTableList {

	public static final ResourceLocation CHEST_VILLAGE_BLACKSMITH = register("dungeon_loot");

	private static ResourceLocation register(String id) {
		return new ResourceLocation(Reference.MODID, id);
	}
}
