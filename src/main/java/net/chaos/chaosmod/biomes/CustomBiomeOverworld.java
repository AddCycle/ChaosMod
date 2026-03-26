package net.chaos.chaosmod.biomes;

import java.util.Random;

import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.world.gen.overworld.WorldGenCustomTree;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class CustomBiomeOverworld extends AbstractBiome {
	public static final WorldGenCustomTree TREE_GEN = new WorldGenCustomTree(
	        ModBlocks.CUSTOM_LOG.getDefaultState(),
	        ModBlocks.CUSTOM_LEAVES.getDefaultState()
	            .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE)
	            .withProperty(BlockLeaves.DECAYABLE, Boolean.TRUE),
	        ModBlocks.BOSS_ALTAR.getDefaultState()
	    );
	
	public CustomBiomeOverworld() {
        super(new Biome.BiomeProperties("Giant Mountains")
            .setBaseHeight(Biomes.MUTATED_EXTREME_HILLS_WITH_TREES.getBaseHeight())
            .setHeightVariation(0.1F)
            .setTemperature(0.4F)
            .setSnowEnabled()
            .setWaterColor(0xffff)
            .setRainDisabled());

        this.decorator.treesPerChunk = 5;

        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();

        this.decorator.generateFalls = false;

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();

        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 2, 5, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPicsou.class, 2, 5, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPolarBear.class, 4, 8, 20));
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySnowman.class, 1, 3, 20));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityWitch.class, 1, 3, 20));
        this.spawnableCaveCreatureList.add(new SpawnListEntry(EntityWitherSkeleton.class, 1, 3, 20));

        this.flowers.clear();
        this.addFlower(ModBlocks.CUSTOM_FLOWER.getDefaultState(), 5);
    }

	@Override
    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        return TREE_GEN;
    }
	
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0x07bdf5;
	}
	
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return 0x07bdf5;//TODO:tweak
	}
}