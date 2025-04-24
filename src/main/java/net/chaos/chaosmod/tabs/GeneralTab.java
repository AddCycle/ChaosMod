package net.chaos.chaosmod.tabs;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.swing.TextComponent;

public class GeneralTab extends CreativeTabs {

	public GeneralTab(String label, String image) {
		super(label);
		this.setBackgroundImageName(image);
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.OXONIUM);
	}
	
	@Override
	public int getLabelColor() {
		return 16747264;
	}

}
