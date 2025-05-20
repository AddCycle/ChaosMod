package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

// Bosses loots
public class ItemHeart extends ItemBase {

	public ItemHeart(String name) {
		super(name);
	}
	
	@Override
	public FontRenderer getFontRenderer(ItemStack stack) {
		// TODO Auto-generated method stub
		return super.getFontRenderer(stack);
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MISC, ModTabs.ITEMS };
    }

}
