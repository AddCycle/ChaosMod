package net.chaos.chaosmod.client.inventory;

import com.google.common.collect.Lists;

import net.chaos.chaosmod.items.necklace.OxoniumNecklace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class AccessoryInventory implements IInventory {
	private ItemStack[] slots = new ItemStack[1];
	private EntityPlayer player;
	
	public AccessoryInventory(EntityPlayer player) {
        this.player = player;
        slots[0] = ItemStack.EMPTY;
    }
	
	@Override
	public String getName() {
		return "AccessoryInventory";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentString(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return 1; // 1 slot
	}

	@Override
	public boolean isEmpty() {
		return this.slots[0].isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return slots[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(Lists.asList(slots[0], slots), index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack item = slots[index];
        slots[index] = ItemStack.EMPTY;
        return item;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		slots[index] = stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
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
		return stack.getItem() instanceof OxoniumNecklace;
	}

	// TODO : FIXME THESE METHODS !!!!!!!!!!!!
	@Override
	public int getField(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getFieldCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	// TODO : FIXME THESE METHODS !!!!!!!!!!!!

	@Override
	public void clear() {
		slots[0] = ItemStack.EMPTY;
	}

}
