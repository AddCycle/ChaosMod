package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChestUpgradeBase extends ItemBase {
	public ChestUpgradeBase(String name) {
		super(name);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState blockstate = worldIn.getBlockState(pos);
		if (Block.isEqualTo(blockstate.getBlock(), Blocks.CHEST)) {
			NBTTagCompound prev_chest_tag = new NBTTagCompound();
			NBTTagCompound empty = new NBTTagCompound();
			worldIn.getTileEntity(pos).writeToNBT(prev_chest_tag);
			worldIn.getTileEntity(pos).readFromNBT(empty);
			worldIn.setBlockState(pos, ModBlocks.OXONIUM_CHEST.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getDirectionFromEntityLiving(pos, player)));
			worldIn.getTileEntity(pos).readFromNBT(prev_chest_tag);
			player.getHeldItemMainhand().shrink(1);
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MATERIALS, ModTabs.GENERAL_TAB };
    }

}
