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
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import util.Reference;

public class ModBiomes {
	public static final List<Biome> BIOMES = new ArrayList<Biome>();
	public static final int CUSTOM_BIOME_WEIGHT = 5;

	public static Biome GIANT_MOUNTAIN = new CustomBiomeOverworld().setRegistryName(new ResourceLocation(Reference.MODID, "giant_mountains"));
	public static Biome NETHER_CAVES = new CustomBiomeNether().setRegistryName(new ResourceLocation(Reference.MODID, "nether_caves"));
	public static Biome ENDER_GARDEN = new CustomBiomeEnderGarden().setRegistryName(new ResourceLocation(Reference.MODID, "ender_garden"));
	public static Biome CHAOS_LAND_BIOME = new CustomBiomeChaosLand().setRegistryName(new ResourceLocation(Reference.MODID, "chaos_land_biome"));
	public static Biome CUSTOM_HELL = new CustomBiomeHell().setRegistryName(new ResourceLocation(Reference.MODID, "custom_hell"));

    public static void registerBiomes() {
    	BIOMES.add(GIANT_MOUNTAIN);
        BIOMES.add(NETHER_CAVES);
        BIOMES.add(ENDER_GARDEN);
        BIOMES.add(CHAOS_LAND_BIOME);
        BIOMES.add(CUSTOM_HELL);
    }
    
    public static void init() {
    	addBiome(ModBiomes.GIANT_MOUNTAIN, BiomeDictionary.Type.MOUNTAIN, BiomeDictionary.Type.COLD);
        addBiome(ModBiomes.NETHER_CAVES);
        addBiome(ModBiomes.ENDER_GARDEN);
        addBiome(ModBiomes.CHAOS_LAND_BIOME);
    }
    
    public static void addBiome(Biome biome, BiomeDictionary.Type ...types) {
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(biome, CUSTOM_BIOME_WEIGHT));
        BiomeDictionary.addTypes(biome, types);
    }
}