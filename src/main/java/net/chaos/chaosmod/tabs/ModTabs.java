package net.chaos.chaosmod.tabs;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.init.ModPotionTypes;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.NonNullList;

public class ModTabs extends GeneralTab {

	public ModTabs(String label, String image, Item icon, Block block, boolean flag) {
		super(label, image, icon, block, flag);
	}

	//Custom ChaosMod Tabs -- feel free to add more
	public static final CreativeTabs BUILDING_BLOCKS = new GeneralTab("chaosmodtab", "general_bg.png", null, ModBlocks.OXONIUM_ORE, true);
	public static final CreativeTabs ITEMS = new GeneralTab("chaosmodtab2", "general_bg.png", ModItems.OXONIUM_NECKLACE, null, false);
	public static final CreativeTabs GENERAL_TAB = new GeneralTab("chaosmodtab3", "general_bg.png", ModItems.OXONIUM_UPGRADE, null, false);
	public static final CreativeTabs POTIONS = new GeneralTab("chaosmodtab4", "general_bg.png", Items.POTIONITEM, null, false) {
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> items) {
		    items.add(PotionUtils.addPotionToItemStack(
		        new ItemStack(Items.POTIONITEM), ModPotionTypes.VIKING_FRIEND_TYPE));
		}
	};
	
}
