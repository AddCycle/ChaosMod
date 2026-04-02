package net.chaos.chaosmod.biomes.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenFlowerPatch extends WorldGenerator {
	public Block flower;
	public IBlockState state;

	public WorldGenFlowerPatch(Block flowerIn) {
        this.setGeneratedBlock(flowerIn, flowerIn.getDefaultState());
	}

	public void setGeneratedBlock(Block flowerIn) {
		this.setGeneratedBlock(flowerIn, flowerIn.getDefaultState());
	}

	public void setGeneratedBlock(Block flowerIn, IBlockState state) {
		this.flower = flowerIn;
		this.state = state;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		ChunkPos chunk = new ChunkPos(position);
		for (int i = 0; i < 32; ++i) {
			if (rand.nextInt(20) != 0) continue;
			BlockPos blockpos = position.add(rand.nextInt(16), 0, rand.nextInt(16));
			
			if (new ChunkPos(blockpos).x != chunk.x || new ChunkPos(blockpos).z != chunk.z) {
			    break;
			}

			if (!worldIn.isBlockLoaded(blockpos)) continue; // try fixing cascading worldgen lag

			blockpos = worldIn.getHeight(blockpos);

			BlockPos down = blockpos.down();
			if (!worldIn.isBlockLoaded(down)) continue; // try

			IBlockState downState = worldIn.getBlockState(down);

			if (worldIn.isAirBlock(blockpos) && canSustainPlant(downState, worldIn, down)) {
				worldIn.setBlockState(blockpos, this.state, 2);
			}
		}

		return true;
	}

	private boolean canSustainPlant(IBlockState state, World worldIn, BlockPos downPos) {
		return state.getBlock().canSustainPlant(state, worldIn, downPos, EnumFacing.UP, (BlockSapling) Blocks.SAPLING) && state.getBlock() != Blocks.GRASS_PATH;
	}
}