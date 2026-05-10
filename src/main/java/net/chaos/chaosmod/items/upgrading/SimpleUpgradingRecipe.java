package net.chaos.chaosmod.items.upgrading;

import net.chaos.chaosmod.inventory.InventoryUpgrading;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SimpleUpgradingRecipe implements IUpgradingRecipe {
	private ItemStack input1, input2, result;

	public SimpleUpgradingRecipe(ItemStack input1, ItemStack input2, ItemStack result) {
		this.input1 = input1;
		this.input2 = input2;
		this.result = result;
	}

	@Override
	public boolean matches(InventoryUpgrading inv, World worldIn) {
		ItemStack stack1 = inv.getStackInSlot(0);
		ItemStack stack2 = inv.getStackInSlot(1);
		if (stack1.isEmpty() || stack2.isEmpty()) return false;
		if ((ItemStack.areItemStacksEqual(stack1, input1) || input1.isEmpty())
		&& (ItemStack.areItemStacksEqual(stack2, input2) || input2.isEmpty())) {
			return true;
		}
		return false;
	}

	@Override
	public ItemStack getUpgradingResult(InventoryUpgrading inv) {
		return result;
	}
}