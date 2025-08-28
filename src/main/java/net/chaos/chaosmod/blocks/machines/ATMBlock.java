package net.chaos.chaosmod.blocks.machines;

import javax.annotation.Nullable;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.BlockContainerBase;
import net.chaos.chaosmod.tileentity.TileEntityATM;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import util.Reference;

public class ATMBlock extends BlockContainerBase {
	public ATMBlock(String name, Material materialIn) {
		super(name, materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityATM();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        } else {
        	ILockableContainer ilockablecontainer = this.getLockableContainer(worldIn, pos);

        	if (ilockablecontainer != null) {
        		playerIn.openGui(Main.instance, Reference.GUI_ATM_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        	}

            return true;
        }
	}
	
    @Nullable
    public ILockableContainer getLockableContainer(World worldIn, BlockPos pos)
    {
        return this.getContainer(worldIn, pos, true);
    }

    @Nullable
    public ILockableContainer getContainer(World worldIn, BlockPos pos, boolean allowBlocking)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (!(tileentity instanceof TileEntityATM))
        {
            return null;
        }
        else
        {
            ILockableContainer ilockablecontainer = (TileEntityATM) tileentity;

            if (!allowBlocking) // && this.isBlocked(worldIn, pos))
            {
                return null;
            }
            else
            {
                return ilockablecontainer;
            }
        }
    }

}