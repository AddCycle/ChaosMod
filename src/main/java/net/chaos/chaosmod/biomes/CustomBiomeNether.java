package net.chaos.chaosmod.biomes;

import java.util.Random;

import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.decoration.BlockCustomFlower;
import net.chaos.chaosmod.blocks.decoration.CustomLeaves;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.world.gen.overworld.WorldGenCustomTree;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeHell;

public class CustomBiomeNether extends BiomeHell {
	public static final WorldGenCustomTree TREE_GEN = new WorldGenCustomTree(
	        ((CustomLog) ModBlocks.CUSTOM_LOG).getStateFromMeta(1),
	        ((CustomLeaves) ModBlocks.CUSTOM_LEAVES).getStateFromMeta(1)
	            .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE)
	            .withProperty(BlockLeaves.DECAYABLE, Boolean.TRUE),
	        ModBlocks.BOSS_ALTAR.getDefaultState()
	    );
	
	public CustomBiomeNether() {
        super(new Biome.BiomeProperties("Nether Caves")
            .setBaseHeight(Biome.getBiome(162).getBaseHeight())
            .setHeightVariation(0.1F)
            .setTemperature(1.0F)
            .setWaterColor(65535)
            .setRainDisabled());

        this.decorator.treesPerChunk = 0;
        this.flowers.clear();
        this.decorator.flowersPerChunk = 8;
        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.STONE.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.decorator.generateFalls = false;
        this.decorator.extraTreeChance = 0.0F;

        this.spawnableCreatureList.add(new SpawnListEntry(EntityPigZombie.class, 4, 8, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityBlaze.class, 1, 3, 30));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPicsou.class, 75, 1, 1));
        this.addFlower(((BlockCustomFlower) ModBlocks.CUSTOM_FLOWER).getStateFromMeta(1), 10);
        
        // ModBiomes.BIOMES.add(this);
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
		// return 0x7ca4f7;
		// return 0x1a60f0;
		return 0xff0000;
		// return ColorizerGrass.getGrassColor(0.0F, 0.5F);
	}
}