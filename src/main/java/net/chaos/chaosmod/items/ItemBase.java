package net.chaos.chaosmod.items;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import util.IHasModel;

// Basic item initialization as a basic item along with a name
// If you want to add Color to text format your items for example, you can add: **MODT** code in strings
public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MATERIALS, ModTabs.ITEMS }; // You can add multiple tabs
    }

}
