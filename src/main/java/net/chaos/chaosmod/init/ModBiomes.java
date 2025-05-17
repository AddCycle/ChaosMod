package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.biomes.CustomBiomeChaosLand;
import net.chaos.chaosmod.biomes.CustomBiomeEnderGarden;
import net.chaos.chaosmod.biomes.CustomBiomeNether;
import net.chaos.chaosmod.biomes.CustomBiomeOverworld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import util.Reference;

public class ModBiomes {
	public static final List<Biome> BIOMES = new ArrayList<Biome>();

	public static final Biome GIANT_MOUNTAIN = new CustomBiomeOverworld();
	public static final Biome NETHER_CAVES = new CustomBiomeNether();
	public static final Biome ENDER_GARDEN = new CustomBiomeEnderGarden();
	public static final Biome CHAOS_LAND_BIOME = new CustomBiomeChaosLand();

    public static void registerBiomes() {
        GIANT_MOUNTAIN.setRegistryName(new ResourceLocation(Reference.MODID, "giant_mountains"));
        NETHER_CAVES.setRegistryName(new ResourceLocation(Reference.MODID, "nether_caves"));
        ENDER_GARDEN.setRegistryName(new ResourceLocation(Reference.MODID, "ender_garden"));
        CHAOS_LAND_BIOME.setRegistryName(new ResourceLocation(Reference.MODID, "chaos_land_biome"));
    }

}
