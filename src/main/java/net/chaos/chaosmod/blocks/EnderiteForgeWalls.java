package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.material.Material;

public class EnderiteForgeWalls extends ConnectedBlock {

	public EnderiteForgeWalls(String name, Material material) {
		super(name, material);

		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockBase(this).setRegistryName(getRegistryName()));
	}

}
