package net.chaos.chaosmod.blocks.complex.mimic;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StartupCommon {
	public static ShapeShifterBlock blockCamouflage;
	public static ItemBlock itemBlockCamouflage;

	public static void preInitCommon()
	{
		blockCamouflage = (ShapeShifterBlock)(new ShapeShifterBlock().setUnlocalizedName("shape_shifter_block"));
		blockCamouflage.setRegistryName("shape_shifter_block");
		ForgeRegistries.BLOCKS.register(blockCamouflage);

		itemBlockCamouflage = new ItemBlock(blockCamouflage);
		itemBlockCamouflage.setRegistryName(blockCamouflage.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlockCamouflage);
	}
}