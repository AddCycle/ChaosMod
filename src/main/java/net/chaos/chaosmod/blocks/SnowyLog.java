package net.chaos.chaosmod.blocks;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.BlockLog;
import net.minecraft.item.Item;
import util.IHasModel;

public class SnowyLog extends BlockLog implements IHasModel {
	
	public SnowyLog(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ModTabs.GENERAL_TAB);
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(Item.getItemFromBlock(this).setRegistryName(getRegistryName()));
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}