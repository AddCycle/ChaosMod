package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

// This class is only designed to help register the Block in multiple CreativeTabs
public class ItemBlockBase extends ItemBlock {

	public ItemBlockBase(Block block) {
		super(block);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { ModTabs.GENERAL_TAB, ModTabs.BUILDING_BLOCKS }; // just add yours here
	}
}
