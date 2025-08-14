package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.tileentity.TileEntityDrawer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDrawer extends BlockContainerBase {

	public BlockDrawer(String name, Material materialIn) {
		super(name, materialIn);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDrawer();
	}

}
