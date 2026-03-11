package net.chaos.chaosmod.items.tools;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemPickaxe;
import proxy.IItemModel;

public class ToolPickaxe extends ItemPickaxe implements IItemModel {

	public ToolPickaxe(String name, ToolMaterial material) {
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setHarvestLevel("pickaxe", 3);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.TOOLS, ModTabs.ITEMS };
    }
}