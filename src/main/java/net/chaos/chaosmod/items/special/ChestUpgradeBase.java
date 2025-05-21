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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
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
		// Thanks Patrick for this part flemme
		IBlockState blockstate = worldIn.getBlockState(pos);
		if (Block.isEqualTo(blockstate.getBlock(), Blocks.CHEST)) {
		    TileEntity tile = worldIn.getTileEntity(pos);
		    if (!(tile instanceof TileEntityChest)) return EnumActionResult.FAIL;

		    TileEntityChest baseChest = (TileEntityChest) tile;
		    TileEntityChest otherChest = null;
		    BlockPos otherChestPos = null;

		    for (EnumFacing direction : EnumFacing.HORIZONTALS) {
		        BlockPos neighborPos = pos.offset(direction);
		        TileEntity neighborTile = worldIn.getTileEntity(neighborPos);
		        if (neighborTile instanceof TileEntityChest) {
		            otherChest = (TileEntityChest) neighborTile;
		            otherChestPos = neighborPos;
		            break;
		        }
		    }

		    // Save both chests' inventories
		    NBTTagCompound baseTag = new NBTTagCompound();
		    baseChest.writeToNBT(baseTag);

		    NBTTagCompound otherTag = null;
		    if (otherChest != null) {
		        otherTag = new NBTTagCompound();
		        otherChest.writeToNBT(otherTag);
		    }

		    // Replace both blocks
		    worldIn.setBlockState(pos, ModBlocks.OXONIUM_CHEST.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getDirectionFromEntityLiving(pos, player)));
		    if (otherChestPos != null) {
		        worldIn.setBlockState(otherChestPos, Blocks.CHEST.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getDirectionFromEntityLiving(otherChestPos, player)));
		    }

		    // Restore NBT
		    TileEntity newBaseChest = worldIn.getTileEntity(pos);
		    if (newBaseChest != null && baseTag != null) {
		        newBaseChest.readFromNBT(baseTag);
		    }

		    if (otherChestPos != null && otherTag != null) {
		        TileEntity newOtherChest = worldIn.getTileEntity(otherChestPos);
		        if (newOtherChest != null) {
		            newOtherChest.readFromNBT(otherTag);
		        }
		    }

		    player.getHeldItemMainhand().shrink(1);
		    return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MATERIALS, ModTabs.ITEMS };
    }

}
