package net.chaos.chaosmod.biomes;

import java.util.Random;

import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.decoration.CustomLeaves;
import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.world.gen.WorldGenCustomTree;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class CustomBiomeChaosLand extends Biome {
	public static final WorldGenCustomTree TREE_GEN = new WorldGenCustomTree(
	        ((CustomLog) ModBlocks.CUSTOM_LOG).getStateFromMeta(3),
	        ((CustomLeaves) ModBlocks.CUSTOM_LEAVES).getStateFromMeta(3)
	            .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE)
	            .withProperty(BlockLeaves.DECAYABLE, Boolean.TRUE),
	        ModBlocks.BOSS_ALTAR.getDefaultState()
	    );
	
	public CustomBiomeChaosLand() {
        super(new Biome.BiomeProperties("Chaos Land")
            .setBaseHeight(Biome.getBiome(162).getBaseHeight())
            .setHeightVariation(0.1F)
            .setTemperature(0.4F)
            .setSnowEnabled()
            .setWaterColor(65535)
            .setRainDisabled());

        this.decorator.treesPerChunk = 0;
        this.flowers.clear();
        this.decorator.flowersPerChunk = 8;
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.DIRT.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.decorator.generateFalls = false;
        this.decorator.extraTreeChance = 0.0F;

        this.spawnableCreatureList.add(new SpawnListEntry(EntityCow.class, 4, 8, 30));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 5, 7, 20));
        this.spawnableCreatureList.add(new SpawnListEntry(EntitySheep.class, 4, 8, 10));
        this.spawnableMonsterList.add(new SpawnListEntry(EntityCreeper.class, 2, 5, 5));
        // this.spawnableCreatureList.add(new SpawnListEntry(EntityMountainGiantBoss.class, 75, 1, 1));
        this.addFlower(ModBlocks.CUSTOM_FLOWER.getDefaultState(), 10);
        
    }
	
	@Override
	public void decorate(World worldIn, Random rand, BlockPos pos) {
	    super.decorate(worldIn, rand, pos);

	    int trees = 5;
	    for (int i = 0; i < trees; i++) {
	        BlockPos genPos = pos.add(rand.nextInt(16) + 8, 0, rand.nextInt(16) + 8);
	        genPos = worldIn.getHeight(genPos);

	        BlockPos checkPos = genPos.down();

	        // Cancel if the block below is one of the custom leaves
	        IBlockState stateBelow = worldIn.getBlockState(checkPos);
	        if (stateBelow.getBlock() instanceof BlockLeaves
	        	|| stateBelow.getBlock() == Blocks.WATER
	        	|| stateBelow.getBlock() == Blocks.FLOWING_WATER) {
	        	return; // Skip this tree
	        }
	        TREE_GEN.generate(worldIn, rand, genPos);
	    }
	}
	
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0x54d65c; // green
		// return ColorizerGrass.getGrassColor(0.0F, 0.5F);
	}

}
