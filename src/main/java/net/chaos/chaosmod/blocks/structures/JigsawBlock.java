package net.chaos.chaosmod.blocks.structures;

import java.util.Random;

import javax.annotation.Nullable;

import net.chaos.chaosmod.blocks.BlockContainerBase;
import net.chaos.chaosmod.tileentity.TileEntityJigsaw;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class JigsawBlock extends BlockContainerBase {
	// maybe add a mode later
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public JigsawBlock() {
		super("jigsaw_block", Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityJigsaw();
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity instanceof TileEntityJigsaw ? ((TileEntityJigsaw) tileentity).usedBy(playerIn) : false;
	}
	
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		if (worldIn.isRemote) return;
		TileEntity tileentity = worldIn.getTileEntity(pos);
		if (tileentity instanceof TileEntityJigsaw) {
			TileEntityJigsaw tileentityjigsaw = (TileEntityJigsaw) tileentity;
			tileentityjigsaw.createdBy(placer);
		}
	}
	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
	}
	
	/** REWRITE THEM if you add properties or modes **/
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, getFacing(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int i = ((EnumFacing) state.getValue(FACING)).getIndex(); // just OR them together with a specific value for example a bool property |= 8, then to retrieve, meta & 8
		return i;
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (worldIn.isRemote) return;

            TileEntity tileentity = worldIn.getTileEntity(pos);

            if (tileentity instanceof TileEntityJigsaw)
            {
                TileEntityJigsaw tileentityjigsaw = (TileEntityJigsaw)tileentity;
                boolean flag = worldIn.isBlockPowered(pos);
                /**
                boolean flag1 = tileentityjigsaw.isPowered();

                if (flag && !flag1)
                {
//                    tileentityjigsaw.setPowered(true);
                    this.trigger(tileentityjigsaw);
                }
                else if (!flag && flag1)
                {
                    tileentityjigsaw.setPowered(false);
                }
                **/
            }
	}
	
	/**
	private void trigger(TileEntityJigsaw tileentity) {
        switch (tileentity.getMode())
        {
            case SAVE:
                tileentity.save(false);
                break;
            case LOAD:
                tileentity.load(false);
                break;
            case CORNER:
                tileentity.unloadStructure();
            case DATA:
        }
	}
	**/

    @Nullable
    public static EnumFacing getFacing(int meta)
    {
        int i = meta & 7;
        return i > 5 ? null : EnumFacing.getFront(i);
    }
}