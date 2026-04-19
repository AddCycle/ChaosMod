package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.biomes.BiomeDenseForest;
import net.chaos.chaosmod.world.gen.experimental.biomes.ExperimentalBiome;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import util.Reference;

public class ModBiomes {
	public static final List<Biome> BIOMES = new ArrayList<Biome>();
	public static final int CUSTOM_BIOME_WEIGHT = 5;

	public static Biome DENSE_FOREST = new BiomeDenseForest().setRegistryName(new ResourceLocation(Reference.MODID, "dense_forest"));
//	public static Biome NETHER_CAVES = new CustomBiomeNether().setRegistryName(new ResourceLocation(Reference.MODID, "nether_caves"));
//	public static Biome ENDER_GARDEN = new CustomBiomeEnderGarden().setRegistryName(new ResourceLocation(Reference.MODID, "ender_garden"));
//	public static Biome CHAOS_LAND_BIOME = new CustomBiomeChaosLand().setRegistryName(new ResourceLocation(Reference.MODID, "chaos_land_biome"));
//	public static Biome CUSTOM_HELL = new CustomBiomeHell().setRegistryName(new ResourceLocation(Reference.MODID, "custom_hell"));
	public static Biome EXPERIMENTAL = new ExperimentalBiome().setRegistryName(new ResourceLocation(Reference.MODID, "experimental_biome"));

    public static void registerBiomes() {
    	BIOMES.add(DENSE_FOREST);
//        BIOMES.add(NETHER_CAVES);
//        BIOMES.add(ENDER_GARDEN);
//        BIOMES.add(CHAOS_LAND_BIOME);
//        BIOMES.add(CUSTOM_HELL);
        BIOMES.add(EXPERIMENTAL);
    }
    
    public static void init() {
    	addBiome(ModBiomes.DENSE_FOREST, 10, BiomeType.WARM, BiomeDictionary.Type.FOREST, BiomeDictionary.Type.CONIFEROUS, BiomeDictionary.Type.DENSE);
//        addBiome(ModBiomes.NETHER_CAVES);
//        addBiome(ModBiomes.ENDER_GARDEN);
//        addBiome(ModBiomes.CHAOS_LAND_BIOME);
        addBiome(ModBiomes.EXPERIMENTAL, 10, BiomeType.WARM, BiomeDictionary.Type.PLAINS);
    }
    
    public static void addBiome(Biome biome, int weight, BiomeType type, BiomeDictionary.Type ...types) {
        BiomeManager.addBiome(type, new BiomeManager.BiomeEntry(biome, weight));
        BiomeDictionary.addTypes(biome, types);
    }
}