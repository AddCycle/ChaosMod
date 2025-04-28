package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import util.Reference;

public class ForgeInterfaceBlock extends BlockBase implements ITileEntityProvider {

	// For the TileEntity it's a container
	public ForgeInterfaceBlock(String name, Material material) {
		super(name, material);
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
            playerIn.openGui(Main.instance, Reference.GUI_FORGE_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityForge();
	}
	
}
