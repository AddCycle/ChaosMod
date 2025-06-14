package net.chaos.chaosmod.items.shield;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemShield;
import util.IHasModel;

public class CustomItemShield extends ItemShield implements IHasModel {
	
	public CustomItemShield(String name) {
		super();
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ ModTabs.ITEMS }; // You can add multiple tabs
    }

}
