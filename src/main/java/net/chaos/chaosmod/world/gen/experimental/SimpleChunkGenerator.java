package net.chaos.chaosmod.world.gen.experimental;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class SimpleChunkGenerator implements IChunkGenerator {
	private final World world;
	private final Random rand;
	private NoiseGeneratorPerlin noise;
	private NoiseGeneratorPerlin waterDepthNoise;

	private static final double NOISE_SCALE = 0.01;
	private static final double NOISE_WATER_SCALE = 0.9;
	private static final int SEA_LEVEL = 32;
	private static final int HEIGHT_RANGE = 4;
	private static final int WATER_DEPTH_RANGE = 10;

	public SimpleChunkGenerator(World world, long seed) {
		this.world = world;
		this.rand = new Random(seed);
		this.noise = new NoiseGeneratorPerlin(this.rand, 4);
		this.waterDepthNoise = new NoiseGeneratorPerlin(rand, 4);
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		ChunkPrimer primer = new ChunkPrimer();

		for (int lx = 0; lx < 16; lx++) {
			for (int lz = 0; lz < 16; lz++) {

				int worldX = x * 16 + lx;
				int worldZ = z * 16 + lz;

				double surfaceValue = noise.getValue(worldX * NOISE_SCALE, worldZ * NOISE_SCALE);
				double waterDepthValue = waterDepthNoise.getValue(worldX * NOISE_WATER_SCALE, worldZ * NOISE_WATER_SCALE);

				// compressing values preventing them from going too deep in the ground
				if (waterDepthValue < 0) {
					waterDepthValue *= 0.2;
				}

				int surfaceHeight = (int) (SEA_LEVEL + surfaceValue * HEIGHT_RANGE);
				int waterDepth = (int) (SEA_LEVEL + waterDepthValue * WATER_DEPTH_RANGE);

				surfaceHeight = MathHelper.clamp(surfaceHeight, 1, 255);
				waterDepth = MathHelper.clamp(surfaceHeight, 1, 255);

				primer.setBlockState(lx, 0, lz, Blocks.BEDROCK.getDefaultState());

				for (int y = 1; y < surfaceHeight; y++) {
					primer.setBlockState(lx, y, lz, Blocks.STONE.getDefaultState());
				}

				if (surfaceHeight >= SEA_LEVEL) primer.setBlockState(lx, surfaceHeight - 1, lz, Blocks.GRASS.getDefaultState());

				for (int y = waterDepth; y < SEA_LEVEL; y++) {
					primer.setBlockState(lx, y, lz, Blocks.WATER.getDefaultState());
				}
			}
		}

		Chunk chunk = new Chunk(this.world, primer, x, z);
		chunk.generateSkylightMap();
		return chunk;
	}

	@Override
	public void populate(int x, int z) {}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z) {
		return false;
	}

	@Override
	public List<SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		return Lists.<SpawnListEntry>newArrayList();
	}

	@Override
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position,
			boolean findUnexplored) {
		return null;
	}

	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z) {}

	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
		return false;
	}
}
