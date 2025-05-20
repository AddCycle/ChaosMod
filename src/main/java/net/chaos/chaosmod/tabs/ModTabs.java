package net.chaos.chaosmod.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.text.TextComponentString;

public class ModTabs extends GeneralTab {

	public ModTabs(String label, String image) {
		super(label, image);
	}

	//Custom ChaosMod Tabs -- feel free to add more
	public static final CreativeTabs GENERAL_TAB = new GeneralTab("chaosmodtab", "general_bg.png");
	public static final CreativeTabs BUILDING_BLOCKS = new GeneralTab("chaosmodtab2", "general_bg.png");
	public static final CreativeTabs ITEMS = new GeneralTab("chaosmodtab3", "general_bg.png");
	
}
