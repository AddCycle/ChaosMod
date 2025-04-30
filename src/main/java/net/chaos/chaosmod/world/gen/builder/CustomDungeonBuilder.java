package net.chaos.chaosmod.world.gen.builder;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.chaos.chaosmod.world.gen.WorldGenCustomDungeon;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class CustomDungeonBuilder extends WorldGenerator implements IWorldGenerator {
	public static final Logger LOGGER = LogManager.getLogger();
	
	public CustomDungeonBuilder() {}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		new DungeonBuilder(worldIn, position, rand).generate();
		LOGGER.info("Generating structure at: {}", position);
		System.out.println("GENERATING structure at : " + position);
		return true;
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0) {
			int x = chunkX * 16 + 8;
	        int z = chunkZ * 16 + 8;
	        int y = 64; // choose a suitable Y level
	        BlockPos pos = new BlockPos(x, y, z);

	        // Actually trigger your structure logic here
	        generate(world, random, pos);
	        LOGGER.info("Generating structure at: {}", pos);
		}
	}

	public class DungeonBuilder {
	    private final World world;
	    private final Random rand;
	    private final BlockPos origin;

	    public DungeonBuilder(World world, BlockPos origin, Random rand) {
	        this.world = world;
	        this.origin = origin;
	        this.rand = rand;
	    }

	    public void generate() {
	        // Example: generate 3 rooms connected by corridors
	        buildRoom(origin.add(0, 0, 0), 7, 5, 7);
	        buildCorridor(origin.add(7, 0, 3), 5, true);
	        buildRoom(origin.add(12, 0, 0), 7, 5, 7);
	        buildCorridor(origin.add(19, 0, 3), 5, true);
	        buildRoom(origin.add(24, 0, 0), 7, 5, 7);
	    }

	    public void buildRoom(BlockPos start, int width, int height, int depth) {
	        Block wall = Blocks.STONEBRICK;
	        Block floor = Blocks.MOSSY_COBBLESTONE;

	        for (int x = 0; x < width; x++) {
	            for (int y = 0; y < height; y++) {
	                for (int z = 0; z < depth; z++) {
	                    BlockPos pos = start.add(x, y, z);
	                    boolean isWall = x == 0 || x == width - 1 || y == 0 || y == height - 1 || z == 0 || z == depth - 1;
	                    world.setBlockState(pos, isWall ? wall.getDefaultState() : Blocks.AIR.getDefaultState(), 2);
	                }
	            }
	        }

	        // Add floor
	        for (int x = 0; x < width; x++) {
	            for (int z = 0; z < depth; z++) {
	                BlockPos floorPos = start.add(x, -1, z);
	                world.setBlockState(floorPos, floor.getDefaultState(), 2);
	            }
	        }
	    }

	    public void buildCorridor(BlockPos start, int length, boolean horizontal) {
	        for (int i = 0; i < length; i++) {
	            BlockPos base = horizontal ? start.add(i, 0, 0) : start.add(0, 0, i);
	            buildRoom(base, 3, 3, 3);
	        }
	    }
	}
	
}
