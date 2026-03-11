package net.chaos.chaosmod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import util.dimensions.TeleportUtil;

public class WaystoneBlock extends BlockBase {
	public BlockPos destination;

	public WaystoneBlock(String name, Material material) {
		super(name, material);
	}

	public WaystoneBlock(String name, Material material, BlockPos pos) {
		super(name, material);
		this.destination = pos;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			TeleportUtil.teleport(playerIn, playerIn.dimension, destination.getX(), destination.getY(), destination.getZ());
		}
		return true;
	}

}
