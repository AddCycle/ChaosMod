package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import util.IHasModel;
import util.Reference;

public class ForgeInterfaceBlock extends BlockContainer implements ITileEntityProvider,IHasModel {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	// For the TileEntity it's a container
	public ForgeInterfaceBlock(String name, Material material) {
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.EAST));
		
        ModBlocks.BLOCKS.add(this);
        ModItems.ITEMS.add(new ItemBlockFurnaces(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	private BlockPos[] calculate_positions_center(BlockPos pos, EnumFacing facing) {
		BlockPos right = pos.offset(facing.rotateAround(Axis.Y));
		BlockPos left = pos.offset(facing.rotateAround(Axis.Y).getOpposite());
		return new BlockPos[]
				{
					pos,
					pos.up(),
					pos.down(),
					right,
					right.down(),
					right.up(),
					left,
					left.up(),
					left.down()
				};
	}
	
	private BlockPos[] calculate_positions(BlockPos pos, EnumFacing facing) {
		BlockPos right = pos.offset(facing.rotateAround(Axis.Y));
		BlockPos left = pos.offset(facing.rotateAround(Axis.Y).getOpposite());
		return new BlockPos[]
				{
					pos.up(),
					pos.down(),
					right,
					right.down(),
					right.up(),
					left,
					left.up(),
					left.down()
				};
	}
	
	/*
	 * This function is for checking the setup of the ChaosForge 3x3 structure
	 */
	private boolean checkSetup(EntityPlayer player, BlockPos pos, World worldIn, EnumFacing facing) {
		BlockPos center = pos.offset(facing.getOpposite());
		BlockPos center2 = pos.offset(facing.getOpposite(), 2);
		BlockPos[] positions = calculate_positions(pos, facing);
		BlockPos[] positions2 = calculate_positions_center(center, facing);
		BlockPos[] positions3 = calculate_positions_center(center2, facing);
		for (BlockPos p : positions) {
			// System.out.println("facing : " + facing);
			BlockPos curr_pos = p;
			Block to_check = worldIn.getBlockState(curr_pos).getBlock();
			if (to_check != ModBlocks.ENDERITE_BRICKS) return false;
			// System.out.println("position is good : " + p + " | BLOCK : " + to_check);
		}
		for (BlockPos p : positions2) {
			BlockPos curr_pos = p;
			Block to_check = worldIn.getBlockState(curr_pos).getBlock();
			if (to_check != ModBlocks.ENDERITE_BRICKS) return false;
		}
		for (BlockPos p : positions3) {
			BlockPos curr_pos = p;
			Block to_check = worldIn.getBlockState(curr_pos).getBlock();
			if (to_check != ModBlocks.ENDERITE_BRICKS) return false;
		}
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote && checkSetup(playerIn, pos, worldIn, facing)) {
            playerIn.openGui(Main.instance, Reference.GUI_FORGE_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityForge();
	}

	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }
    
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }

    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	TileEntity tileentity = worldIn.getTileEntity(pos);
        if (tileentity instanceof TileEntityForge) {
            TileEntityForge forge = (TileEntityForge) tileentity;
            for (int i = 0; i < forge.getSizeInventory(); ++i) {
                ItemStack stack = forge.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    InventoryHelper.spawnItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                }
            }
            worldIn.updateComparatorOutputLevel(pos, this);
        }
    	super.breakBlock(worldIn, pos, state);
    }
	
}
