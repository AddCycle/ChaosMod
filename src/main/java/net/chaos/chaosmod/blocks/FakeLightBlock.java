package net.chaos.chaosmod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;

public class FakeLightBlock extends BlockBase {

	public FakeLightBlock(String name) {
		super(name, Material.AIR);
	}
	
	@Override
	public int getLightValue(IBlockState state) {
	    return 15;
	}
	
	@Override
	public boolean isCollidable() {
		return false;
	}
	
	/*@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer;
	}*/
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.INVISIBLE;
	}
}
