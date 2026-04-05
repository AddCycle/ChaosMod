package net.chaos.chaosmod.fluids;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class DirtyWaterFluid extends Fluid {

	public DirtyWaterFluid(String fluidName, ResourceLocation still, ResourceLocation flowing,
			ResourceLocation overlay) {
		super(fluidName, still, flowing, overlay);
		setUnlocalizedName(fluidName);
	}
}