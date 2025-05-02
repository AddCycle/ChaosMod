package net.chaos.chaosmod.biomes;

import net.chaos.chaosmod.entity.boss.entities.EntityMountainGiantBoss;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class CustomBiome extends Biome {
	
	public CustomBiome() {
        super(new Biome.BiomeProperties("Giant Mountains")
            .setBaseHeight(Biome.getBiome(162).getBaseHeight())
            .setHeightVariation(0.1F)
            .setTemperature(0.4F)
            .setSnowEnabled()
            .setWaterColor(65535)
            .setRainDisabled());

        this.decorator.flowersPerChunk = 8;
        this.topBlock = Blocks.SNOW.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();

        this.spawnableCreatureList.add(new SpawnListEntry(EntityMountainGiantBoss.class, 75, 1, 1));
        this.addFlower(ModBlocks.CUSTOM_FLOWER.getDefaultState(), 5);
    }

}
