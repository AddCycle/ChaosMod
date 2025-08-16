package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.tileentity.TileEntityDrawer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDrawer extends BlockContainerBase {
	private int capacity;

	public BlockDrawer(String name, Material materialIn) {
		super(name, materialIn);
		this.setHarvestLevel("axe", 0);
		this.capacity = 64 * 9;
	}

	public BlockDrawer(String name, Material materialIn, int capacity) {
		super(name, materialIn);
		this.capacity = Math.abs(capacity);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDrawer(capacity);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return moveContentToDrawer(worldIn, pos, playerIn, hand);
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		if (worldIn.isRemote) return;
	    if (!playerIn.isSneaking()) return;
	    moveContentToPlayerInventory(worldIn, pos, playerIn, EnumHand.MAIN_HAND);
	}
	
	public boolean moveContentToDrawer(World world, BlockPos pos, EntityPlayer player, EnumHand hand) {
		if (world.isRemote) return true;

	    TileEntity tile = world.getTileEntity(pos);
	    if (!(tile instanceof TileEntityDrawer)) return false;

	    TileEntityDrawer drawer = (TileEntityDrawer) tile;
	    ItemStack held = player.getHeldItem(hand);
	    
	    if (!held.isEmpty()) {
	    	int before = held.getCount();
	    	Main.getLogger().info("adding count : {}", before);
	    	int added = drawer.addStack(held);
	    	held.shrink(added);
	    	Main.getLogger().info("added items count : {}", added);
	    }
	    
	    return true;
	}

	public void moveContentToPlayerInventory(World world, BlockPos pos, EntityPlayer player, EnumHand hand) {
	    TileEntity tile = world.getTileEntity(pos);
	    if (!(tile instanceof TileEntityDrawer)) return;

	    TileEntityDrawer drawer = (TileEntityDrawer) tile;
	    ItemStack removed = drawer.removeStack(drawer.getStack().getCount());
	    if (!removed.isEmpty()) {
	        if (!player.addItemStackToInventory(removed)) {
	            world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ, removed));
	        }
	        Main.getLogger().info("{} items taken from drawer", removed.getCount());
	    }
	}
}
