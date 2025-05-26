package net.chaos.chaosmod.villagers;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class CustomProfessions {
	public static final VillagerProfession CUSTOM_FARMER = new CustomBlacksmithProfession();
	
	public static void registerCustomProfessions() {
		ForgeRegistries.VILLAGER_PROFESSIONS.register(CUSTOM_FARMER);
		ForgeRegistries.VILLAGER_PROFESSIONS.forEach(t -> System.out.println(t.getRegistryName()));
	}
}
