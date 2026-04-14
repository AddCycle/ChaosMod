package net.chaos.chaosmod.fluids.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.chaos.chaosmod.fluids.BlockFluidClassicBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

/**
 * PLAYTEST : too efficient needs balance INFO : precision, it's like every time
 * you put a new fertilized water source so I need to delay the time it starts
 * to act as a fertilizer, maybe after 5 minutes or check it's neighbors if
 * there's already one source
 * Should be better balanced now test if still working
 */
public class BlockFertilizedWater extends BlockFluidClassicBase {
	private List<BlockPos> growables = new ArrayList<>();
	/* totalWorldTime in ticks */
	private long addedTime;

	public BlockFertilizedWater(String name, Fluid fluid, Material material, MapColor mapColor) {
		super(name, fluid, material, mapColor);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		addedTime = world.getTotalWorldTime();

		super.onBlockAdded(world, pos, state);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		// 2 min
		if (world.getTotalWorldTime() - addedTime > 2400) {
			fertilizeNearby(world, pos, rand);
		}

		super.updateTick(world, pos, state, rand);
	}

	private void fertilizeNearby(World world, BlockPos pos, Random rand) {
		if (world.isRemote)
			return;

		int r = 4;
		for (BlockPos targetPos : BlockPos.getAllInBoxMutable(pos.add(-r, -1, -r), pos.add(r, 1, r))) {

			IBlockState state = world.getBlockState(targetPos);
			Block block = state.getBlock();

			if (block instanceof IGrowable) {
				IGrowable growable = (IGrowable) block;

				if (growable.canGrow(world, targetPos, state, false) && growable.canUseBonemeal(world, rand, pos, state)
						&& !(growable instanceof BlockGrass) && !(growable instanceof BlockTallGrass)
						&& !(growable instanceof BlockFlower))
					growables.add(targetPos.toImmutable());
			}
		}

		if (!growables.isEmpty()) {
			int attempts = 6;

			for (int i = 0; i < attempts; i++) {
				BlockPos target = growables.get(rand.nextInt(growables.size()));
				growCrop(world, (IGrowable) world.getBlockState(target).getBlock(), target, world.getBlockState(target),
						rand);
			}
		}

		growables.clear();
	}

	private void growCrop(World world, IGrowable growable, BlockPos targetPos, IBlockState state, Random rand) {
		if (world.isRemote)
			return;

		if (rand.nextInt(5) != 0)
			return;
		growable.grow(world, rand, targetPos, state);
		world.playEvent(2005, targetPos, 0); // bonemeal particles packet
	}
}