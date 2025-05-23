package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFireball;
import util.IHasModel;

public class CustomFireball extends ItemFireball implements IHasModel {
	
	public CustomFireball(String name) {
		setCreativeTab(CreativeTabs.MISC);
		setUnlocalizedName(name);
		setRegistryName(name);

		ModItems.ITEMS.add(this);
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		// TODO Auto-generated method stub
		return new CreativeTabs[] { ModTabs.ITEMS, CreativeTabs.MISC };
	}

}
