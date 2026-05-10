package net.chaos.chaosmod.blocks.decoration;

import net.chaos.chaosmod.blocks.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockHoneycomb extends BlockBase {

	public BlockHoneycomb() {
//		super("honeycomb_block", ModMaterials.SOLID_HONEY);
		super("honeycomb_block", Material.ROCK);
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
	    return true;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
	    return true;
	}

	@Override
	public boolean isTopSolid(IBlockState state) {
	    return true;
	}
	
	@Override
	public boolean canEntitySpawn(IBlockState state, Entity entityIn) {
		return true;
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, SpawnPlacementType type) {
		return true;
	}
}