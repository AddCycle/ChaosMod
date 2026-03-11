package net.chaos.chaosmod.items;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import proxy.IItemModel;

/**
 * Basic Item-subclass to initialize custom items inside creativeTabs
 */
public class ItemBase extends Item implements IItemModel {

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MATERIALS, ModTabs.ITEMS };
    }
}
