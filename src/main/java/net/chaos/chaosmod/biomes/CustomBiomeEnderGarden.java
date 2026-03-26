package net.chaos.chaosmod.biomes;

import java.util.Random;

import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.decoration.BlockCustomFlower;
import net.chaos.chaosmod.blocks.decoration.CustomLeaves;
import net.chaos.chaosmod.entity.EntityForgeGuardian;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.world.gen.overworld.WorldGenCustomTree;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class CustomBiomeEnderGarden extends AbstractBiome {
	public static final WorldGenCustomTree TREE_GEN = new WorldGenCustomTree(
	        ((CustomLog) ModBlocks.CUSTOM_LOG).getStateFromMeta(2),
	        ((CustomLeaves) ModBlocks.CUSTOM_LEAVES).getStateFromMeta(2)
	            .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE)
	            .withProperty(BlockLeaves.DECAYABLE, Boolean.TRUE),
	        ModBlocks.BOSS_ALTAR.getDefaultState()
	    );
	
	public CustomBiomeEnderGarden() {
        super(new Biome.BiomeProperties("Ender Garden")
            .setBaseHeight(Biomes.MUTATED_EXTREME_HILLS_WITH_TREES.getBaseHeight())
            .setHeightVariation(0.1F)
            .setTemperature(0.4F)
            .setSnowEnabled()
            .setWaterColor(65535)
            .setRainDisabled());

        this.decorator.treesPerChunk = 5;

        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();

        this.decorator.generateFalls = false;

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 2, 5, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPicsou.class, 2, 5, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityForgeGuardian.class, 4, 8, 20));
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySnowman.class, 1, 3, 20));

        this.flowers.clear();
        this.addFlower(((BlockCustomFlower) ModBlocks.CUSTOM_FLOWER).getStateFromMeta(2), 5);
    }
	
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0x5f08a6;
	}
	
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return 0x5f08a6; // TODO : tweak
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return TREE_GEN;
	}
}
