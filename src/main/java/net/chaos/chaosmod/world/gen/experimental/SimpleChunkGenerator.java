package net.chaos.chaosmod.world.gen.experimental;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
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

	private static final double NOISE_SCALE = 0.01;
	private static final int BASE_HEIGHT = 64;
	private static final int HEIGHT_RANGE = 5;

	public SimpleChunkGenerator(World world, long seed) {
		this.world = world;
		this.rand = new Random(seed);
		this.noise = new NoiseGeneratorPerlin(this.rand, 4); // 4 octaves
	}

	@Override
	public Chunk generateChunk(int x, int z) {
		ChunkPrimer primer = new ChunkPrimer();

		for (int lx = 0; lx < 16; lx++) {
			for (int lz = 0; lz < 16; lz++) {

				int worldX = x * 16 + lx;
				int worldZ = z * 16 + lz;

				double sample = noise.getValue(worldX * NOISE_SCALE, worldZ * NOISE_SCALE);
				int height = (int) (BASE_HEIGHT + sample * HEIGHT_RANGE);

				for (int y = 0; y < height; y++) {
					primer.setBlockState(lx, y, lz, Blocks.STONE.getDefaultState());
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
