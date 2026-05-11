package net.chaos.chaosmod.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class InventoryUpgrading implements IInventory {
    private final NonNullList<ItemStack> stackList;
    private final ContainerUpgradingStation eventHandler;
    
    public InventoryUpgrading(ContainerUpgradingStation eventHandler) {
    	stackList = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
    	this.eventHandler = eventHandler;
	}

	@Override
	public String getName() {
		return "Upgrading";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public int getSizeInventory() {
		return stackList.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack stack : stackList) {
			if (!stack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index >= this.getSizeInventory() ? ItemStack.EMPTY : (ItemStack)this.stackList.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {

        ItemStack itemstack = ItemStackHelper.getAndSplit(this.stackList, index, count);

        if (!itemstack.isEmpty())
        {
            this.getEventHandler().onUpgradingChanged(this);
        }

        return itemstack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(stackList, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
        this.stackList.set(index, stack);
        this.getEventHandler().onUpgradingChanged(this);
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
        this.stackList.clear();
	}
	
	public void consumeInputs() {
	    ItemStackHelper.getAndSplit(this.stackList, 0, 1);
	    ItemStackHelper.getAndSplit(this.stackList, 1, 1);
	}

	public ContainerUpgradingStation getEventHandler() {
		return eventHandler;
	}
}