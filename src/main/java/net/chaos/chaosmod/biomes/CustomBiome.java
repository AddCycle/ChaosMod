package net.chaos.chaosmod.biomes;

import net.chaos.chaosmod.entity.boss.entities.EntityMountainGiantBoss;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class CustomBiome extends Biome {
	
	public CustomBiome() {
        super(new Biome.BiomeProperties("Giant Mountains")
            .setBaseHeight(Biome.getBiome(162).getBaseHeight())
            .setHeightVariation(0.1F)
            .setTemperature(0.4F)
            .setSnowEnabled()
            .setRainDisabled());

        this.decorator.flowersPerChunk = 5;
        this.topBlock = Blocks.SNOW_LAYER.getDefaultState();
        this.fillerBlock = Blocks.SNOW.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();

        // this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 100, 2, 4));
        // this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 80, 4, 8));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityMountainGiantBoss.class, 100, 1, 1));

        // Add entities, decorations, etc if you like
    }

}
