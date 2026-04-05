package net.chaos.chaosmod.fluids.blocks;

import net.chaos.chaosmod.fluids.BlockFluidClassicBase;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockDirtyWater extends BlockFluidClassicBase {

	public BlockDirtyWater(String name, Fluid fluid, Material material, MapColor mapColor) {
		super(name, fluid, material, mapColor);
	}
}