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
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

// FIXME : add this to a pre or decorate event just after generating trees
// FIXME : cascading worldgenlag, consider using chunkPos instead of just getPos in the forge event
public class WorldGenTreeBark extends WorldGenerator {
	private final Block log;
	private final IBlockState state;

	public WorldGenTreeBark(Block log) {
		this(log, log.getDefaultState());
	}

	public WorldGenTreeBark(Block log, IBlockState state) {
		this.log = log;
		this.state = state;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		ChunkPos chunkPos = new ChunkPos(position);

		for (int i = 0; i < 16; ++i) {
        	if (rand.nextInt(20) != 0) continue;

			BlockPos blockpos = position.add(rand.nextInt(16), 0, rand.nextInt(16));

			if (!worldIn.isBlockLoaded(blockpos)) continue; // try fixing cascading worldgen lag

			blockpos = worldIn.getHeight(blockpos);

			List<BlockPos> validBlocks = new ArrayList<>();

			EnumFacing randomFacing = Plane.HORIZONTAL.random(rand);

			int barkLength = rand.nextInt(3) + 3; // min 3, max 5

			if (!worldIn.isBlockLoaded(blockpos.offset(randomFacing, barkLength - 1))) continue; // try
			for (int j = 0; j < barkLength; j++) {
				BlockPos triedPos = blockpos.offset(randomFacing, j);

				// try again
				if (new ChunkPos(triedPos).x != chunkPos.x || new ChunkPos(triedPos).z != chunkPos.z) {
					break;
				}

				BlockPos triedDown = triedPos.down();

				IBlockState groundState = worldIn.getBlockState(triedDown);

				if (worldIn.isAirBlock(triedPos) && canSustainLog(groundState, worldIn, triedDown)) {
				    validBlocks.add(triedPos);
				}
			}

			if (validBlocks.size() != barkLength)
				continue; // retry another pos

			for (BlockPos pos : validBlocks) {
				worldIn.setBlockState(pos, this.getLogOrientation(randomFacing), 2);
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