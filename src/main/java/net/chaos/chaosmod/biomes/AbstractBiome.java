package net.chaos.chaosmod.biomes;

import java.util.Random;

import javax.annotation.Nullable;

import net.chaos.chaosmod.biomes.gen.CustomWorldGenFlowers;
import net.chaos.chaosmod.blocks.decoration.BlockCustomFlower;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

/**
 * This class create an entirely customizable with my removal of most of the
 * default vanilla features :
 * TODO : needs optimization on flower and other things gen might reconsider and move it on forge events
 * or inside a WorldGenerator
 */
public abstract class AbstractBiome extends Biome {
	private CustomWorldGenFlowers customFlowerGen = new CustomWorldGenFlowers(
			(BlockCustomFlower) ModBlocks.CUSTOM_FLOWER);
	protected int flowersPerChunk; /* My custom entry and generation */
	private int totalFlowerWeight;

	public AbstractBiome(BiomeProperties properties) {
		super(properties);
		this.decorator.treesPerChunk = 0;
		this.decorator.extraTreeChance = 0f;
		this.decorator.flowersPerChunk = 0;
		this.flowersPerChunk = 8;
	}

	@Override
	public abstract WorldGenAbstractTree getRandomTreeFeature(Random rand);

	@Override
	public void decorate(World world, Random random, BlockPos pos) {
		super.decorate(world, random, pos);

		ChunkPos forgeChunkPos = new ChunkPos(pos);
		if (TerrainGen.decorate(world, random, forgeChunkPos, DecorateBiomeEvent.Decorate.EventType.FLOWERS)) {
			generateFlowers(world, random, pos, this.flowersPerChunk);
		}
	}

	/**
	 * Register a new plant to be planted when bonemeal is used on grass && to be
	 * generated as well
	 * 
	 * @param state  The block to place.
	 * @param weight The weight of the plant, where red flowers are 10 and yellow
	 *               flowers are 20.
	 */
	@Override
	public void addFlower(IBlockState state, int weight) {
		this.flowers.add(new FlowerEntry(state, weight));
		this.totalFlowerWeight += weight;
	}

	private void generateFlowers(World world, Random random, BlockPos pos, int flowersPerChunk) {
		for (int l2 = 0; l2 < flowersPerChunk; ++l2) {
			int i7 = random.nextInt(16) + 8;
			int l10 = random.nextInt(16) + 8;
			int j14 = world.getHeight(this.decorator.chunkPos.add(i7, 0, l10)).getY() + 32;

			if (j14 > 0) {
                int k17 = random.nextInt(j14);
                BlockPos blockpos1 = this.decorator.chunkPos.add(i7, k17, l10);

				IBlockState flowerState = this.getRandomFlower(random, blockpos1);
				if (flowerState == null)
					continue;
				BlockBush blockflower = (BlockBush) flowerState.getBlock();

				customFlowerGen.setGeneratedBlock(blockflower, flowerState);
				customFlowerGen.generate(world, random, blockpos1);
			}
		}
	}

	@Nullable
	private IBlockState getRandomFlower(Random rand, BlockPos pos) {
		int totalWeight = this.totalFlowerWeight;

		int r = rand.nextInt(totalWeight);

		for (FlowerEntry f : this.flowers) {
			r -= f.itemWeight;
			if (r < 0) {
				return f.state;
			}
		}

		return null;
	}
}