package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class OxoniumBlock extends BlockBase {

	public OxoniumBlock(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 2); // wood/gold = 0, stone = 1, iron = 2, diamond = 3
		setResistance(15.0f);
		setHardness(5.0f);
		setLightLevel(1.0f);
		setCreativeTab(ModTabs.GENERAL_TAB);
	}
	
}