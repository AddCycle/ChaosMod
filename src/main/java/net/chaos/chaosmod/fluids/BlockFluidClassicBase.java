package net.chaos.chaosmod.fluids;

import net.chaos.chaosmod.init.ModFluidBlocks;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
public class BlockFluidClassicBase extends BlockFluidClassic {

	public BlockFluidClassicBase(String name, Fluid fluid, Material material, MapColor mapColor) {
		super(fluid, material, mapColor);
		setRegistryName(name);
		setUnlocalizedName(name);
		
		ModFluidBlocks.FLUID_BLOCKS.add(this);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
