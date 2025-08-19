package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.tileentity.TileEntityDrawer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockDrawer extends BlockContainerBase {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	private int capacity;

	public BlockDrawer(String name, Material materialIn) {
		this(name, materialIn, 64 * 9);
	}

	public BlockDrawer(String name, Material materialIn, int capacity) {
		super(name, materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.setHarvestLevel("axe", 0);
		this.capacity = Math.abs(capacity);
	}	
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
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
	
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
		if (!worldIn.isRemote) { // server side only
	        TileEntity te = worldIn.getTileEntity(pos);
	        if (te instanceof TileEntityDrawer) { // if your TileEntity stores items
	            TileEntityDrawer tile = (TileEntityDrawer) te;

	            ItemStack stack = tile.getStack();
	            if (!stack.isEmpty()) {
	            	InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
	            }
	        }
	    }

		super.onBlockHarvested(worldIn, pos, state, player);
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
	
	@Override
	public IBlockState getStateForPlacement(
	        World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta,
	        EntityLivingBase placer) {
	    return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
	    return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
	    return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
	    EnumFacing facing = EnumFacing.getHorizontal(meta);
	    return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
	    return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, FACING);
	}
}
