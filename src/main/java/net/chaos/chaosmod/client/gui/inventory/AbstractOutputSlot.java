package net.chaos.chaosmod.client.gui.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public abstract class AbstractOutputSlot extends Slot {
    private final EntityPlayer player;
    private int removeCount;

	public AbstractOutputSlot(EntityPlayer player, IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		this.player = player;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false; // because it's an output slot no item is allowed to put
	}
	
	@Override
	public ItemStack decrStackSize(int amount) {
		if (this.getHasStack()) this.removeCount += Math.min(amount, this.getStack().getCount());
		return super.decrStackSize(amount);
	}
	
	@Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
		this.onCrafting(stack);
		super.onTake(thePlayer, stack);
		return stack;
	}

}
