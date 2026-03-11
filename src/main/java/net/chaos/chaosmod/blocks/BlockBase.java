package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import proxy.IBlockModel;

/**
 * Basic Block-subclass with name & material
 */
public class BlockBase extends Block implements IBlockModel {
	
	public BlockBase(String name, Material material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHardness(2.0f);
		setResistance(10.0f);
	
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(this.getRegistryName()));
	}
}
