package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.fluids.blocks.BlockDirtyWater;
import net.chaos.chaosmod.fluids.blocks.BlockFertilizedWater;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

public class ModFluidBlocks {
	public static final List<Block> FLUID_BLOCKS = new ArrayList<Block>();
	
	public static final Block FERTILIZED_WATER_BLOCK = new BlockFertilizedWater("fertilized_water", ModFluids.FERTILIZED_WATER, Material.WATER, MapColor.GREEN);
	public static final Block DIRTY_WATER_BLOCK = new BlockDirtyWater("dirty_water", ModFluids.DIRTY_WATER, Material.LAVA, MapColor.DIRT);


	public static void registerCustomMeshesAndStates() {
		registerFluidRendering(ModFluidBlocks.FERTILIZED_WATER_BLOCK);
		registerFluidRendering(ModFluidBlocks.DIRTY_WATER_BLOCK);
	}

	private static void registerFluidRendering(Block fluidBlock) {
		Main.getLogger().info("Registering mesh definition & state mapper for : {}", fluidBlock.getRegistryName());

		ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(fluidBlock), new ItemMeshDefinition() {
			@Override
			public ModelResourceLocation getModelLocation(ItemStack stack) {
				return new ModelResourceLocation(fluidBlock.getRegistryName(), "fluid");
			}
		});
		
		ModelLoader.setCustomStateMapper(fluidBlock, new StateMapperBase() {
			
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return new ModelResourceLocation(fluidBlock.getRegistryName(), "fluid");
			}
		});
	}
}