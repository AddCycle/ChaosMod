package net.chaos.chaosmod.biomes;

import java.util.Random;

import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.decoration.BlockCustomFlower;
import net.chaos.chaosmod.blocks.decoration.CustomLeaves;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.world.gen.overworld.WorldGenCustomTree;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class CustomBiomeChaosLand extends AbstractBiome {
	public static final WorldGenCustomTree TREE_GEN = new WorldGenCustomTree(
	        ((CustomLog) ModBlocks.CUSTOM_LOG).getStateFromMeta(3),
	        ((CustomLeaves) ModBlocks.CUSTOM_LEAVES).getStateFromMeta(3)
	            .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE)
	            .withProperty(BlockLeaves.DECAYABLE, Boolean.TRUE),
	        ModBlocks.BOSS_ALTAR.getDefaultState()
	    );
	
	public CustomBiomeChaosLand() {
        super(new Biome.BiomeProperties("Chaos Land")
            .setBaseHeight(Biomes.PLAINS.getBaseHeight())
            .setHeightVariation(0.1F)
            .setTemperature(0.4F)
            .setSnowEnabled()
            .setWaterColor(65535)
            .setRainDisabled());

        this.decorator.treesPerChunk = 5;

        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();

        this.decorator.generateFalls = false; // lava/water lakes

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 4, 8, 30));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 7, 20));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPicsou.class, 4, 8, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 2, 5, 5));

        this.flowers.clear();
        this.addFlower(((BlockCustomFlower) ModBlocks.CUSTOM_FLOWER).getStateFromMeta(3), 10);
        
    }
	
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0x54d65c; // green
	}

	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return 0x54d65c; // TODO : tweak
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return TREE_GEN;
	}
}
