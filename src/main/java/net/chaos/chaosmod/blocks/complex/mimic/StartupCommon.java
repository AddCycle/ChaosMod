package net.chaos.chaosmod.blocks.complex.mimic;

import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class StartupCommon {
	public static ShapeShifterBlock blockCamouflage;  // this holds the unique instance of your block
	public static ItemBlock itemBlockCamouflage;  // this holds the unique instance of the ItemBlock corresponding to your block

	public static void preInitCommon()
	{
		// each instance of your block should have two names:
		// 1) a registry name that is used to uniquely identify this block.  Should be unique within your mod.  use lower case.
		// 2) an 'unlocalised name' that is used to retrieve the text name of your block in the player's language.  For example-
		//    the unlocalised name might be "water", which is printed on the user's screen as "Wasser" in German or
		//    "aqua" in Italian.
		//
		//    Multiple blocks can have the same unlocalised name - for example
		//  +----RegistryName----+---UnlocalisedName----+
		//  |  flowing_water     +       water          |
		//  |  stationary_water  +       water          |
		//  +--------------------+----------------------+
		//
		blockCamouflage = (ShapeShifterBlock)(new ShapeShifterBlock().setUnlocalizedName("shape_shifter_block"));
		blockCamouflage.setRegistryName("shape_shifter_block");
		ForgeRegistries.BLOCKS.register(blockCamouflage);

		// We also need to create and register an ItemBlock for this block otherwise it won't appear in the inventory
		itemBlockCamouflage = new ItemBlock(blockCamouflage);
		itemBlockCamouflage.setRegistryName(blockCamouflage.getRegistryName());
		ForgeRegistries.ITEMS.register(itemBlockCamouflage);
	}

}
