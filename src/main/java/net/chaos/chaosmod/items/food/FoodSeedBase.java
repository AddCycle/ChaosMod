package net.chaos.chaosmod.items.food;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemSeedFood;
import proxy.IItemModel;

public class FoodSeedBase extends ItemSeedFood implements IItemModel {

	public FoodSeedBase(String name, int amount, float saturation, Block crops, Block soil) {
		super(amount, saturation, crops, soil);
		setUnlocalizedName(name);
		setRegistryName(name);
		
		ModItems.ITEMS.add(this);
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.FOOD, ModTabs.ITEMS };
    }
}
