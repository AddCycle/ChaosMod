package net.chaos.chaosmod.init;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.fluids.FertilizedWaterFluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {
	public static final Fluid FERTILIZED_WATER = new FertilizedWaterFluid("fertilized_water", new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/water_flow"), new ResourceLocation("blocks/water_overlay"));

	public static void registerFluids() {
		Main.getLogger().info("UNIVERSAL BUCKET STATE : {}", FluidRegistry.isUniversalBucketEnabled());
		registerFluid(FERTILIZED_WATER);
	}

	public static void registerFluid(Fluid fluid) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
	}

}
