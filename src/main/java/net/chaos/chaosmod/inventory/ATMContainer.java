package net.chaos.chaosmod.inventory;

import net.chaos.chaosmod.items.special.ItemMoneyWad;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ATMContainer extends Container {
	private final IInventory atmInventory;
	private final int numRows;

	public ATMContainer(IInventory playerInventory, IInventory atmInventory, EntityPlayer player)
	{
		this.atmInventory = atmInventory;
		this.numRows = atmInventory.getSizeInventory() / 9;
		int i = (this.numRows - 4) * 18;

		for (int j = 0; j < this.numRows; ++j)
		{
			for (int k = 0; k < 9; ++k)
			{
				this.addSlotToContainer(new Slot(atmInventory, k + j * 9, 8 + k * 18, 18 + j * 18) {
					@Override
					public boolean isItemValid(ItemStack stack) {
						return stack.getItem() instanceof ItemMoneyWad;
					}
				});
			}
		}

		// inventory container slot adder below (maybe expend the storage below)

		for (int l = 0; l < 3; ++l)
		{
			for (int j1 = 0; j1 < 9; ++j1)
			{
				this.addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
			}
		}

		// Hotbar
		for (int i1 = 0; i1 < 9; ++i1)
		{
			this.addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		// FIXME : maybe later not... (with blacklisted people)
		return true; // every player should be able to interact with the bank for now
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			int atmSize = this.atmInventory.getSizeInventory();

			if (index < atmSize)
			{
				if (!this.mergeItemStack(itemstack1, atmSize, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 0, atmSize, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}
}