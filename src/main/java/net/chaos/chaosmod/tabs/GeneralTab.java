package net.chaos.chaosmod.tabs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class GeneralTab extends CreativeTabs {

	public GeneralTab(String label, String image) {
		super(label);
		this.setBackgroundImageName(image);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModBlocks.OXONIUM_ORE);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		super.displayAllRelevantItems(list);
		
		/*
		 * Sorting by name
		 */
		// list.sort(Comparator.comparing(ItemStack::getDisplayName)); 

		/*
		 * Sorting by type
		 */
		/*list.clear();
		// OXONIUM
		list.add(new ItemStack(ModBlocks.OXONIUM_ORE));
		list.add(new ItemStack(ModItems.OXONIUM));
		list.add(new ItemStack(ModItems.OXONIUM_SWORD));
		list.add(new ItemStack(ModItems.OXONIUM_PICKAXE));
		list.add(new ItemStack(ModItems.OXONIUM_AXE));
		list.add(new ItemStack(ModItems.OXONIUM_SHOVEL));
		list.add(new ItemStack(ModItems.OXONIUM_HOE));
		list.add(new ItemStack(ModItems.OXONIUM_HELMET));
		list.add(new ItemStack(ModItems.OXONIUM_CHESTPLATE));
		list.add(new ItemStack(ModItems.OXONIUM_LEGGINGS));
		list.add(new ItemStack(ModItems.OXONIUM_BOOTS));
		list.add(new ItemStack(ModItems.OXONIUM_CARROT));
		list.add(new ItemStack(ModBlocks.OXONIUM_BLOCK));
		list.add(new ItemStack(ModBlocks.OXONIUM_STAIRS));
		list.add(new ItemStack(ModBlocks.OXONIUM_BRICKS));
		list.add(new ItemStack(ModBlocks.OXONIUM_CHEST));
		list.add(new ItemStack(ModItems.OXONIUM_UPGRADE));
		list.add(new ItemStack(ModBlocks.OXONIUM_FURNACE));
		
		// ALLEMANITE
		list.add(new ItemStack(ModBlocks.ALLEMANITE_ORE));
		list.add(new ItemStack(ModItems.ALLEMANITE_INGOT));
		list.add(new ItemStack(ModItems.ALLEMANITE_EXTINGUISHER));
		list.add(new ItemStack(ModItems.ALLEMANITE_BACKPACK));
		list.add(new ItemStack(ModBlocks.ALLEMANITE_BLOCK));
		list.add(new ItemStack(ModBlocks.ALLEMANITE_BRICKS));
		
		// ENDERITE
		list.add(new ItemStack(ModBlocks.ENDERITE_ORE));
		list.add(new ItemStack(ModItems.ENDERITE_SHARD));
		list.add(new ItemStack(ModBlocks.ENDERITE_BLOCK));
		list.add(new ItemStack(ModBlocks.ENDERITE_BRICKS));
		
		// OTHER
		list.add(new ItemStack(ModBlocks.BOSS_ALTAR));
		list.add(new ItemStack(ModBlocks.CUSTOM_GRASS));
		list.add(new ItemStack(ModBlocks.CUSTOM_FLOWER));*/
	}
	
	@Override
	public int getLabelColor() {
		return 16747264;
	}
	
	@Override
	public boolean hasSearchBar() {
		return false;
	}

}
