package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.fluids.DirtyWaterFluid;
import net.chaos.chaosmod.fluids.FertilizedWaterFluid;
import net.chaos.chaosmod.fluids.HoneyFluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import util.Reference;

public class ModFluids {
	public static final List<Fluid> FLUIDS = new ArrayList<>();
	
	public static final Fluid FERTILIZED_WATER = new FertilizedWaterFluid("fertilized_water", new ResourceLocation(Reference.MODID, "fluids/fertilized_water_still"), new ResourceLocation(Reference.MODID, "fluids/fertilized_water_flow"), new ResourceLocation(Reference.MODID, "fluids/fertilized_water_overlay"));
	public static final Fluid DIRTY_WATER = new DirtyWaterFluid("dirty_water", new ResourceLocation(Reference.MODID, "fluids/dirty_water_still"), new ResourceLocation(Reference.MODID, "fluids/dirty_water_flow"), new ResourceLocation(Reference.MODID, "fluids/dirty_water_overlay"));
	public static final Fluid HONEY = new HoneyFluid("honey", new ResourceLocation(Reference.MODID, "fluids/honey_still"), new ResourceLocation(Reference.MODID, "fluids/honey_flow"), new ResourceLocation(Reference.MODID, "fluids/honey_overlay"));

	public static void registerFluids() {
		registerFluid(FERTILIZED_WATER);
		registerFluid(DIRTY_WATER);
		registerFluid(HONEY);
	}

	public static void registerFluid(Fluid fluid) {
		FluidRegistry.registerFluid(fluid);
		FluidRegistry.addBucketForFluid(fluid);
		FLUIDS.add(fluid);
	}

	public static void enableUniversalBucket() {
		FluidRegistry.enableUniversalBucket();
	}
}