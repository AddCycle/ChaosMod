package net.chaos.chaosmod.tabs;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class GeneralTab extends CreativeTabs {
	public Item icon;
	public Block icon_;
	public boolean flag;

	public GeneralTab(String label, String image, Item item, Block block, boolean isBlock) {
		super(label);
		this.setBackgroundImageName(image);
		this.icon = item;
		this.icon_ = block;
		this.flag = isBlock;
	}

	@Override
	public ItemStack getTabIconItem() {
		if (this.flag) return new ItemStack(this.icon_); else return new ItemStack(this.icon);
	}
	
	@Override
	public void displayAllRelevantItems(NonNullList<ItemStack> list) {
		super.displayAllRelevantItems(list);
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
