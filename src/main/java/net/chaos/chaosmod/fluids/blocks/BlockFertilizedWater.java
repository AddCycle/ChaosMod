package net.chaos.chaosmod.fluids.blocks;

import java.util.Random;

import net.chaos.chaosmod.fluids.BlockFluidClassicBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class BlockFertilizedWater extends BlockFluidClassicBase {

	public BlockFertilizedWater(String name, Fluid fluid, Material material, MapColor mapColor) {
		super(name, fluid, material, mapColor);
		setTickRandomly(true);
	}
	
	@Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.randomTick(worldIn, pos, state, rand);

        if (rand.nextInt(10) == 0) { // 1/10 chance per tick
            fertilizeNearby(worldIn, pos, rand);
        }
    }

	private void fertilizeNearby(World world, BlockPos pos, Random rand) {
	    int r = 4;
	    for (BlockPos targetPos : BlockPos.getAllInBox(
	            pos.add(-r, -1, -r),
	            pos.add(r, 1, r))) {

	        IBlockState state = world.getBlockState(targetPos);
	        Block block = state.getBlock();

	        if (block instanceof IGrowable) {
	            IGrowable growable = (IGrowable) block;

	            // Server: grow crop and trigger vanilla bonemeal particles
	            if (!world.isRemote && growable.canGrow(world, targetPos, state, false)) {
	                growable.grow(world, rand, targetPos, state);
	                world.playEvent(2005, targetPos, 0);
	            }

	            // Client: spawn happy villager particles directly on crop
	            if (world.isRemote) {
	                double x = targetPos.getX() + 0.5;
	                double z = targetPos.getZ() + 0.5;
	                double y = targetPos.getY() + (block instanceof BlockCrops || block instanceof BlockSapling ? 0.7 : 0.5);

	                int particleCount = 3 + rand.nextInt(3);
	                for (int i = 0; i < particleCount; i++) {
	                    world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, x, y, z, 0, 0, 0);
	                }
	            }
	        }
	    }
	}

}