package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.fluids.blocks.BlockFertilizedWater;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class ModFluidBlocks {
	public static final List<Block> FLUID_BLOCKS = new ArrayList<Block>();
	
	public static final Block FERTILIZED_WATER_BLOCK = new BlockFertilizedWater("fertilized_water", ModFluids.FERTILIZED_WATER, Material.WATER, MapColor.GREEN);

}