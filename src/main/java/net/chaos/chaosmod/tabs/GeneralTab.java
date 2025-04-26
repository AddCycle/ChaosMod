package net.chaos.chaosmod.tabs;

import java.util.Comparator;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class GeneralTab extends CreativeTabs {

	public GeneralTab(String label, String image) {
		super(label);
		this.setBackgroundImageName(image);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.OXONIUM);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		super.displayAllRelevantItems(list);
		
		list.sort(Comparator.comparing(ItemStack::getDisplayName));
	}
	
	@Override
	public int getLabelColor() {
		return 16747264;
	}
	
	@Override
	public boolean hasSearchBar() {
		return true;
	}

}
