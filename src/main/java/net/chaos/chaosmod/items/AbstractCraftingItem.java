package net.chaos.chaosmod.items;

import net.minecraft.item.ItemStack;

public abstract class AbstractCraftingItem extends ItemBase {
	
	public AbstractCraftingItem(String name) {
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return true;
	}
	
	@Override
	public boolean isDamageable() {
		return true;
	}
	
	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		ItemStack stack = itemStack.copy();
		stack.setItemDamage(stack.getItemDamage() + 1);
		if (stack.getItemDamage() >= stack.getMaxDamage()) {
			return ItemStack.EMPTY;
		}
		return stack;
	}
}