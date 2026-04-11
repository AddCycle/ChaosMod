package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.items.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BridgePlacer extends ItemBase {

	public BridgePlacer(String name) {
		super(name);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			placeBridge(worldIn, pos, player.getAdjustedHorizontalFacing());
			player.getHeldItem(hand).shrink(1);
		}
		return EnumActionResult.SUCCESS;
	}

	private void placeBridge(World world, BlockPos pos, EnumFacing facing) {
		
		for (int i = 0; i < 16; i++) {
			world.setBlockState(pos.offset(facing, 1 + i), Blocks.COBBLESTONE.getDefaultState());
		}
	}
}