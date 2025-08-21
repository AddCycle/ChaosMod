package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;

public class ItemBlockFurnaces extends ItemBlock {

	public ItemBlockFurnaces(Block block) {
		super(block);
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { ModTabs.GENERAL_TAB, CreativeTabs.DECORATIONS, CreativeTabs.SEARCH };
	}
}
