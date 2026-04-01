package net.chaos.chaosmod.biomes;

import java.util.Random;

import net.chaos.chaosmod.blocks.CustomLog;
import net.chaos.chaosmod.blocks.decoration.BlockCustomFlower;
import net.chaos.chaosmod.blocks.decoration.CustomLeaves;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.world.gen.overworld.WorldGenCustomTree;
import net.minecraft.block.BlockLeaves;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class CustomBiomeNether extends AbstractBiome {
	public static final WorldGenCustomTree TREE_GEN = new WorldGenCustomTree(
	        ((CustomLog) ModBlocks.CUSTOM_LOG).getStateFromMeta(1),
	        ((CustomLeaves) ModBlocks.CUSTOM_LEAVES).getStateFromMeta(1)
	            .withProperty(BlockLeaves.CHECK_DECAY, Boolean.FALSE)
	            .withProperty(BlockLeaves.DECAYABLE, Boolean.TRUE),
	        ModBlocks.BOSS_ALTAR.getDefaultState()
	    );
	
	public CustomBiomeNether() {
        super(new Biome.BiomeProperties("Nether Caves")
            .setBaseHeight(Biomes.MESA.getBaseHeight())
            .setHeightVariation(0.1F)
            .setTemperature(1.0F)
            .setWaterColor(65535)
            .setRainDisabled());

        this.decorator.treesPerChunk = 5;

        this.topBlock = Blocks.GRASS.getDefaultState();
        this.fillerBlock = Blocks.STONE.getDefaultState();

        this.decorator.generateFalls = false;

        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPigZombie.class, 4, 8, 10));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityBlaze.class, 1, 3, 30));
        this.spawnableCreatureList.add(new SpawnListEntry(EntityPicsou.class, 75, 1, 1));

        this.flowers.clear();
        this.addFlower(((BlockCustomFlower) ModBlocks.CUSTOM_FLOWER).getStateFromMeta(1), 10);
        
    }
	
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return 0xff0000;
	}
	
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return 0xff0000; // TODO : tweak to make leaves stand out over grass
	}

	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return TREE_GEN;
	}
}