package net.chaos.chaosmod.world;

import java.util.Random;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ModWorldGen implements IWorldGenerator {
	private final WorldGenMinable overworldGen;
	private final WorldGenMinable netherGen;

	public ModWorldGen() {
		overworldGen = new WorldGenMinable(ModBlocks.OXONIUM_ORE.getDefaultState(), 15, BlockMatcher.forBlock(Blocks.STONE));
		netherGen = new WorldGenMinable(ModBlocks.ALLEMANITE_ORE.getDefaultState(), 15, BlockMatcher.forBlock(Blocks.NETHERRACK));
	}
	/*
	 * Overworld = 0
	 * End = 1
	 * Nether = -1
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
		case 0:
			generateOverworld(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
			break;
		case -1:
			generateNether(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
			break;
		case 1:
			break;
		}
	}
	
	private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		generateOre(overworldGen, ModBlocks.OXONIUM_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, random.nextInt(7) + 4, 18);
	}

	private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		generateOre(netherGen, ModBlocks.ALLEMANITE_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 0, 100, random.nextInt(7) + 4, 50);
	}

	private void generateOre(WorldGenerator generator, IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {

		int deltaY = maxY - minY;
		for (int i = 0; i < chances; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
			
			// WorldGenMinable generator = new WorldGenMinable(ore, size);
			generator.generate(world, random, pos);
		}
	}

}
