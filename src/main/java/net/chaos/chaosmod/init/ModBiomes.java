package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.biomes.CustomBiomeNether;
import net.chaos.chaosmod.biomes.CustomBiomeOverworld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import util.Reference;

public class ModBiomes {
	public static final List<Biome> BIOMES = new ArrayList<Biome>();

	public static final Biome GIANT_MOUNTAIN = new CustomBiomeOverworld();
	public static final Biome NETHER_CAVES = new CustomBiomeNether();

    public static void registerBiomes() {
        GIANT_MOUNTAIN.setRegistryName(new ResourceLocation(Reference.MODID, "giant_mountains"));
        NETHER_CAVES.setRegistryName(new ResourceLocation(Reference.MODID, "nether_caves"));
    }

}
