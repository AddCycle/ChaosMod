package net.chaos.chaosmod.villagers;

import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;

public class CustomProfessions {
	public static final VillagerProfession CUSTOM_VIKING_TRADER = new CustomBlacksmithProfession();
	
	public static void registerCustomProfessions() {
		ForgeRegistries.VILLAGER_PROFESSIONS.register(CUSTOM_VIKING_TRADER);
		// ForgeRegistries.VILLAGER_PROFESSIONS.forEach(t -> System.out.println(t.getRegistryName()));
	}
}
