package net.chaos.chaosmod.items.tools;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;
import proxy.IItemModel;

public class ToolHoe extends ItemHoe implements IItemModel {

	public ToolHoe(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.TOOLS, ModTabs.ITEMS };
    }
}