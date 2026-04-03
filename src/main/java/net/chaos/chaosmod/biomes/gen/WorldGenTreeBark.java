package net.chaos.chaosmod.biomes.gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldGenTreeBark extends AbstractSafeWorldGenerator {
	private final Block log;

	public WorldGenTreeBark(Block log) {
		this(log, log.getDefaultState());
	}

	public WorldGenTreeBark(Block log, IBlockState state) {
		this.log = log;
		this.setGeneratedBlock(state);
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		int startX = chunkPos.getXStart();
		int endX = chunkPos.getXEnd();

		int startZ = chunkPos.getZStart();
		int endZ = chunkPos.getZEnd();

		Chunk chunk = worldIn.getChunkFromChunkCoords(chunkPos.x, chunkPos.z);

		for (int i = 0; i < 16; ++i) {
        	if (rand.nextInt(20) != 0) continue;

			int x = (startX + 1) + rand.nextInt(15);
			int z = (startZ + 1) + rand.nextInt(15);
			if (x > endX || z > endZ) continue;

			int localX = x & 15;
		    int localZ = z & 15;

		    int y = chunk.getHeightValue(localX, localZ);
		    if (y <= 0) continue;

			BlockPos blockpos = new BlockPos(x, y, z);

			List<BlockPos> validBlocks = new ArrayList<>();

			EnumFacing randomFacing = Plane.HORIZONTAL.random(rand);

			int barkLength = rand.nextInt(3) + 3; // min 3, max 5

			for (int j = 0; j < barkLength; j++) {
				BlockPos triedPos = blockpos.offset(randomFacing, j);
				BlockPos triedDown = triedPos.down();
				
				// If the strip walks outside the loaded chunk, abort the whole strip
			    if (!worldIn.isBlockLoaded(triedPos, false)) break;

				IBlockState groundState = chunk.getBlockState(triedDown);
				IBlockState triedState = chunk.getBlockState(triedPos);

				boolean isAir = triedState.getBlock() == Blocks.AIR;

				if (isAir && canSustainLog(groundState, worldIn, triedDown)) {
				    validBlocks.add(triedPos);
				}
			}

			if (validBlocks.size() != barkLength)
				continue;

			for (BlockPos pos : validBlocks) {
				worldIn.setBlockState(pos, this.getLogOrientation(randomFacing), 2 | 16);
			}
		}

		return true;
	}
	
	private IBlockState getLogOrientation(EnumFacing facing) {
		EnumAxis axis = EnumAxis.fromFacingAxis(facing.getAxis());

	    if (this.state.getProperties().containsKey(BlockLog.LOG_AXIS)) {
	        return this.state.withProperty(BlockLog.LOG_AXIS, axis);
	    }
	    
	    if (this.state.getProperties().containsKey(BlockLog.AXIS)) {
	        return this.state.withProperty(BlockLog.AXIS, facing.getAxis());
	    }
	    
	    return this.state;
	}

	private boolean canSustainLog(IBlockState state, World worldIn, BlockPos downPos) {
		return state.getBlock().canSustainPlant(state, worldIn, downPos, EnumFacing.UP, (BlockSapling) Blocks.SAPLING) && state.getBlock() != Blocks.GRASS_PATH;
	}
}