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

public class WorldGenTallBirchTree extends WorldGenAbstractTree {
	public static final IBlockState BIRCH_LOG = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT,
			BlockPlanks.EnumType.BIRCH);
	public static final IBlockState BIRCH_LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT,
			BlockPlanks.EnumType.BIRCH);
	private static final int flag = 2 | 16;

	public WorldGenTallBirchTree() {
		super(false);
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		int height = world.getHeight(position).getY();
		if (position.getY() != height)
			return false;

		int treeHeight = 7 + rand.nextInt(5);
		int leafHeight = treeHeight - Math.max(2, rand.nextInt(5));

		int flag = 2 | 16;

		IBlockState state = world.getBlockState(position.down());
		Block block = state.getBlock();
		if (!block.canSustainPlant(state, world, position.down(), EnumFacing.UP, (BlockSapling) Blocks.SAPLING)) {
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
		IBlockState leaf = BIRCH_LEAF;
		generateThinTree(world, position, leaf, treeHeight, leafHeight, 1);

		// trunk
		IBlockState log = BIRCH_LOG;
		for (int i = 0; i < treeHeight; i++) {
			world.setBlockState(position.add(0, i, 0), log, flag);
		}

		return true;
	}

	private void generateThinTree(World world, BlockPos position, IBlockState leaf, int treeHeight, int leafHeight, int radius) {

		for (int y = treeHeight - leafHeight; y < treeHeight; y++) {
			for (EnumFacing face : EnumFacing.HORIZONTALS) {
				world.setBlockState(position.add(0, y, 0).offset(face), leaf, flag);
			}
		}
		
		world.setBlockState(position.add(0, treeHeight, 0), leaf, flag);
	}
}