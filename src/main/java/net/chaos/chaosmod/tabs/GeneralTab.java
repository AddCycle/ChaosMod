package net.chaos.chaosmod.tabs;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class GeneralTab extends CreativeTabs {

	public GeneralTab(String label, String image) {
		super(label);
		this.setBackgroundImageName(image);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.OXONIUM);
	}

}
