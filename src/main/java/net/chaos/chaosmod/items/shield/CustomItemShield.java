package net.chaos.chaosmod.items.shield;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.text.TextComponentTranslation;
import util.IHasModel;

public class CustomItemShield extends ItemShield implements IHasModel {
	private String name;

	public CustomItemShield(String name) {
		super();
		this.name = name;
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(1);
		ModItems.ITEMS.add(this);
	}

	public String getItemStackDisplayName(ItemStack stack)
	{
		if (stack.getSubCompound("BlockEntityTag") != null)
		{
			EnumDyeColor enumdyecolor = TileEntityBanner.getColor(stack);
			return new TextComponentTranslation("item." + this.name + "." + enumdyecolor.getUnlocalizedName() + ".name").getFormattedText();
		}
		else
		{
			return new TextComponentTranslation("item." + this.name + ".name").getFormattedText();
		}
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
	{
		return new CreativeTabs[]{ ModTabs.ITEMS, CreativeTabs.COMBAT, CreativeTabs.SEARCH }; // You can add multiple tabs
	}

}
