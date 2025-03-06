package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.material.Material;

public class AllemaniteOre extends BlockBase {

	public AllemaniteOre(String name, Material material) {
		super(name, material);
		setCreativeTab(ModTabs.GENERAL_TAB);
		setHardness(5.0f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 3);
		setLightLevel(17);
	}
}