package net.chaos.chaosmod.items;

import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import util.IHasModel;

// Basic item initialization as a basic item along with a name
// If you want to add Color to text format your items for example, you can add: **MODT** code in strings
public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabs.MATERIALS);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("\u00A7cMateriel : item basique");
		tooltip.add("\u00a7eTest de couleur");
		tooltip.add("\\e[1mCeci est une description en bold stp marche");
	}
	
	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MATERIALS, ModTabs.GENERAL_TAB }; // You can add other tabs
    }

}
