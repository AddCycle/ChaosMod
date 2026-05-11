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
		// stack1.getItemDamage() == 0 additional condition maybe later so damaged items aren't taken accountability of (only undamaged items are accepted...)

		if (stack1.isEmpty() || stack2.isEmpty()) return false;
		
		boolean flag =
			(input1.isEmpty() || stack1.getItem() == input1.getItem()) &&
			(input2.isEmpty() || stack2.getItem() == input2.getItem());

		return flag;
	}

	@Override
	public ItemStack getUpgradingResult(InventoryUpgrading inv) {
		ItemStack output = result.copy(); // this was the issue, if I pass the itemstack stored, of course it will be discarded from the recipe registry needs to make a copy of the stack
		ItemStack inputStack = inv.getStackInSlot(0);
		if (inputStack.hasTagCompound()) {
	        output.setTagCompound(inputStack.getTagCompound().copy());
	    }

		return output;
	}
}