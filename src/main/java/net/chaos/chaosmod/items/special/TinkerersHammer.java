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
		this.setMaxDamage(10);
	}
	
	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		worldIn.playSound(playerIn, playerIn.getPosition(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
		super.onCreated(stack, worldIn, playerIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Wow this thing is really heavy !"); // le travail c'est la santé
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { ModTabs.ITEMS, CreativeTabs.TOOLS };
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}
	
	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
}
