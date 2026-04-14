package net.chaos.chaosmod.fluids.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.chaos.chaosmod.compatibility.mathsmod.MathsModCompatibility;
import net.chaos.chaosmod.fluids.BlockFluidClassicBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

public class BlockFertilizedWater extends BlockFluidClassicBase {
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
		// 10 sec delay to not spam
		if (world.getTotalWorldTime() - addedTime > 200) {
			fertilizeNearby(world, pos, rand);
		}

		super.updateTick(world, pos, state, rand);
	}

	private void fertilizeNearby(World world, BlockPos pos, Random rand) {
		if (world.isRemote)
			return;

		List<BlockPos> growables = new ArrayList<>();

		int r = 4;
		for (BlockPos targetPos : BlockPos.getAllInBoxMutable(pos.add(-r, -1, -r), pos.add(r, 1, r))) {

			IBlockState state = world.getBlockState(targetPos);
			Block block = state.getBlock();

			if (block instanceof IGrowable) {
				IGrowable growable = (IGrowable) block;

				if (growable.canGrow(world, targetPos, state, false)
						&& growable.canUseBonemeal(world, rand, targetPos, state) && !isGarbagePlant(growable))

					growables.add(targetPos.toImmutable());
			} else {
				spreadMathsmodFlowers(world, targetPos.toImmutable(), state, rand);
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
	}

	private void spreadMathsmodFlowers(World world, BlockPos targetPos, IBlockState state, Random rand) {
		if (!Loader.isModLoaded(Reference.MATHSMOD))
			return;

		Block block = state.getBlock();
		if (!isMathsmodFlower(block))
			return;

		spreadFlower(world, targetPos, rand, block);
	}

	private void spreadFlower(World world, BlockPos pos, Random rand, Block block) {
		if (!(world.getBlockState(pos.down()).getBlock() == Blocks.FARMLAND))
			return;
		
		if (!(rand.nextInt(5) == 0)) return;

		if (!isMathsmodFlower(block))
			return;
		Block targetBlock = block;

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i == 0 && j == 0) continue; // skip center
				BlockPos target = pos.add(i, 0, j);

				if (world.getBlockState(target.down()).getBlock() == Blocks.FARMLAND
						&& world.getBlockState(target).getMaterial() == Material.AIR) {
					world.setBlockState(target, targetBlock.getDefaultState());
					playBonemealEffect(world, target);
				}
			}
		}
	}

	private boolean isBlockMatching(Block block, ResourceLocation rl) {
		ResourceLocation rl2 = ForgeRegistries.BLOCKS.getKey(block);
		if (rl2 == null)
			return false;
		return rl.equals(rl2);
	}

	private boolean isMathsmodFlower(Block block) {
		return isBlockMatching(block, MathsModCompatibility.DIAMONA)
				|| isBlockMatching(block, MathsModCompatibility.HELL_FLOWER)
				|| isBlockMatching(block, MathsModCompatibility.DENANIUM);
	}

	/**
	 * True only if NOT grass, tallgrass, flower, sunflower
	 * 
	 * @param growable
	 * @return
	 */
	private boolean isGarbagePlant(IGrowable growable) {
		return (growable instanceof BlockGrass) || (growable instanceof BlockTallGrass)
				|| (growable instanceof BlockDoublePlant) || (growable instanceof BlockFlower);
	}

	/**
	 * spawn "bonemeal" particles packet
	 * 
	 * @param world
	 * @param pos
	 */
	private void playBonemealEffect(World world, BlockPos pos) {
		if (world.isRemote)
			return;

		world.playEvent(2005, pos, 0);
	}

	private void growCrop(World world, IGrowable growable, BlockPos targetPos, IBlockState state, Random rand) {
		if (world.isRemote)
			return;

		if (rand.nextInt(5) != 0)
			return;

		growable.grow(world, rand, targetPos, state);
		playBonemealEffect(world, targetPos);
	}
}