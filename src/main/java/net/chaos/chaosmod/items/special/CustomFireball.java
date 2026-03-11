package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFireball;
import proxy.IItemModel;

public class CustomFireball extends ItemFireball implements IItemModel {
	
	public CustomFireball(String name) {
		setCreativeTab(CreativeTabs.MISC);
		setUnlocalizedName(name);
		setRegistryName(name);

		ModItems.ITEMS.add(this);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { ModTabs.ITEMS, CreativeTabs.MISC };
	}

}
