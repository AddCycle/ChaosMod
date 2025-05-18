package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.biomes.CustomBiomeChaosLand;
import net.chaos.chaosmod.biomes.CustomBiomeEnderGarden;
import net.chaos.chaosmod.biomes.CustomBiomeHell;
import net.chaos.chaosmod.biomes.CustomBiomeNether;
import net.chaos.chaosmod.biomes.CustomBiomeOverworld;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import util.Reference;

public class ModBiomes {
	public static final List<Biome> BIOMES = new ArrayList<Biome>();

	public static final Biome GIANT_MOUNTAIN = new CustomBiomeOverworld().setRegistryName(new ResourceLocation(Reference.MODID, "giant_mountains"));;
	public static final Biome NETHER_CAVES = new CustomBiomeNether().setRegistryName(new ResourceLocation(Reference.MODID, "nether_caves"));;
	public static final Biome ENDER_GARDEN = new CustomBiomeEnderGarden().setRegistryName(new ResourceLocation(Reference.MODID, "ender_garden"));;
	public static final Biome CHAOS_LAND_BIOME = new CustomBiomeChaosLand().setRegistryName(new ResourceLocation(Reference.MODID, "chaos_land_biome"));;
	public static final Biome CUSTOM_HELL = new CustomBiomeHell().setRegistryName(new ResourceLocation(Reference.MODID, "custom_hell"));


    public static void registerBiomes() {
    	BIOMES.add(GIANT_MOUNTAIN);
        BIOMES.add(NETHER_CAVES);
        BIOMES.add(ENDER_GARDEN);
        BIOMES.add(CHAOS_LAND_BIOME);
        BIOMES.add(CUSTOM_HELL);
    }

}
