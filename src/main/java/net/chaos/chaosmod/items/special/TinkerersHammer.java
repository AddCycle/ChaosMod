package net.chaos.chaosmod.items.special;

import java.util.List;

import net.chaos.chaosmod.items.AbstractCraftingItem;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class TinkerersHammer extends AbstractCraftingItem {
	
	public TinkerersHammer(String name) {
		super(name);
		this.setMaxDamage(8);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
		super.onCreated(stack, worldIn, playerIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Are you an engineer ?"); // le travail c'est la santé
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { ModTabs.GENERAL_TAB, CreativeTabs.TOOLS };
	}
}
