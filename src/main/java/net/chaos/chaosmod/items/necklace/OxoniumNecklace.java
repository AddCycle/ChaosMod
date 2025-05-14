package net.chaos.chaosmod.items.necklace;

import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;

public class OxoniumNecklace extends ItemBase {

	public OxoniumNecklace(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { CreativeTabs.COMBAT, ModTabs.GENERAL_TAB };
	}

}