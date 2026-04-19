package net.chaos.chaosmod.biomes.gen.treegen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenRootTree extends WorldGenAbstractTree {

	public WorldGenRootTree() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		int height = world.getHeight(position).getY();
		if (position.getY() != height) return false;
		
		int treeHeight = 4 + rand.nextInt(5);
		int radius = 1 + rand.nextInt(3);
		int leafHeight = 3;
		
		int flag = 2|16;
		
		IBlockState state = world.getBlockState(position.down());
		Block block = state.getBlock();
		if (!block.canSustainPlant(state, world, position.down(), EnumFacing.UP, (BlockSapling)Blocks.SAPLING)) {
			return false;
		}
		
		// enough space to grow trunk
		for (int y = 0; y <= treeHeight; y++) {
		    BlockPos checkPos = position.up(y);
		    if (!world.isAirBlock(checkPos) && !world.getBlockState(checkPos).getMaterial().isReplaceable()) {
		        return false;
		    }
		}

		// leaves
		IBlockState leaf = getRandomLeaf(rand);
		if (radius < 2) {
			generateBlockyTree(world, position, leaf, treeHeight, leafHeight, radius);
		} else {
			generateRoundTree(world, position, leaf, treeHeight, leafHeight, radius);
		}

		// trunk
		IBlockState log = getRandomLog(rand);
		for (int i = 0; i < treeHeight; i++) {
			world.setBlockState(position.add(0, i, 0), log, flag);
		}

		return true;
	}

	private void generateBlockyTree(World world, BlockPos position, IBlockState leaf, int treeHeight, int leafHeight,
			int radius) {
		int flag = 2 | 16;
		for (int y = treeHeight - leafHeight; y <= treeHeight; y++) {
			for (int i = -radius; i <= radius; i++) {
				for (int j = -radius; j <= radius; j++) {
					world.setBlockState(position.add(i, y, j), leaf, flag);
				}
			}
		}
	}

	private void generateRoundTree(World world, BlockPos position, IBlockState leaf, int treeHeight, int leafHeight, int radius) {
		int flag = 2 | 16;
		for (int y = treeHeight - leafHeight; y <= treeHeight; y++) {
		    int layer = y - treeHeight;
		    int r = radius - layer / 2;

		    for (int x = -r; x <= r; x++) {
		        for (int z = -r; z <= r; z++) {
		            if (x*x + z*z <= r*r) {
		                world.setBlockState(position.add(x, y, z), leaf, flag);
		            }
		        }
		    }
		}
	}

	private IBlockState getRandomLog(Random rand) {
		BlockPlanks.EnumType type = BlockPlanks.EnumType.byMetadata(rand.nextInt(4));
		return Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, type);
	}

	private IBlockState getRandomLeaf(Random rand) {
		BlockPlanks.EnumType type = BlockPlanks.EnumType.byMetadata(rand.nextInt(4));
		return Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, type).withProperty(BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));
	}
}