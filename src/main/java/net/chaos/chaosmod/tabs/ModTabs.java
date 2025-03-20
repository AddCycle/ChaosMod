package net.chaos.chaosmod.tabs;

import net.minecraft.creativetab.CreativeTabs;

public class ModTabs extends GeneralTab {

	public ModTabs(String label, String image) {
		super(label, image);
	}

	//Custom ChaosMod Tabs -- feel free to add more
	public static final CreativeTabs GENERAL_TAB = new GeneralTab("chaosmodtab", "general_bg.png");
	// public static final CreativeTabs FORGE = new GeneralTab("chaosmodtab", "general_bg.png");
	
}
