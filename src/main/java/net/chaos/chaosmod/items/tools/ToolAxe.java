package net.chaos.chaosmod.items.tools;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import proxy.IItemModel;

public class ToolAxe extends ItemAxe implements IItemModel {

	public ToolAxe(String name, ToolMaterial material) {
		super(material, 6.0f, -3.2f);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public int getEntityLifespan(ItemStack itemStack, World world) {
		return 12000;
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.TOOLS, ModTabs.ITEMS };
    }
}