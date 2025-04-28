package net.chaos.chaosmod.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ForgeInterfaceContainer extends Container {
	private final IInventory tileEntity;

    public ForgeInterfaceContainer(InventoryPlayer playerInventory, IInventory tileEntity) {
        this.tileEntity = tileEntity;

        // 4 in a row
        for (int i = 0; i < 3; ++i) {
        	this.addSlotToContainer(new Slot(tileEntity, i, 15 + i * 18, 34));
        }
        this.addSlotToContainer(new Slot(tileEntity, 3, 116, 35));

        // Player inventory
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
            }
        }

        // Hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.tileEntity.isUsableByPlayer(playerIn);
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
    	ItemStack itemstack = ItemStack.EMPTY;
    	Slot slot = inventorySlots.get(index);
    	
    	if (slot != null && slot.getHasStack()) {
    		ItemStack stack1 = slot.getStack();
    		itemstack = stack1.copy();
    		int slots_number = 3;
    		if (index < slots_number) {
    			if (!this.mergeItemStack(stack1, slots_number, this.inventorySlots.size(), true)) {
    				return ItemStack.EMPTY;
    			}
    		} else {
    			if (!this.mergeItemStack(stack1, 0, this.inventorySlots.size(), false)) {
    				return ItemStack.EMPTY;
    			}
    		}
    		
    		if (stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
    	}
    	return itemstack;
    }

}
