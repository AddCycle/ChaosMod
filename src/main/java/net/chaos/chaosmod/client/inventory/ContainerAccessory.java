package net.chaos.chaosmod.client.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAccessory extends ContainerPlayer {
	public final AccessoryInventory accessory1;
	// public final AccessoryInventory accessory2;
	// public final AccessoryInventory accessory3;

    public ContainerAccessory(InventoryPlayer playerInventory, boolean localWorld, EntityPlayer player) {
        super(playerInventory, localWorld, player);
        this.accessory1 = new AccessoryInventory(player);
        // this.accessory2 = new AccessoryInventory(player);
        // this.accessory3 = new AccessoryInventory(player);

        // Add custom slot to the container (screen X=80, Y=8 for example)
        int offset = 4;
        this.addSlotToContainer(new SlotAccessory(player, accessory1, 0, offset, -20));
        // this.addSlotToContainer(new SlotAccessory(player, accessory2, 0, offset + 20, -20));
        // this.addSlotToContainer(new SlotAccessory(player, accessory3, 0, offset + 20 * 2, -20));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
    	ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        Container playerContainer = playerIn.inventoryContainer;
        Slot playerSlot = null;
        if (!playerIn.getEntityWorld().isRemote) {
        	playerSlot = playerContainer.getSlot(1);
        	if (slot != null && slot.getHasStack()) {
        		if (playerSlot != null && !playerSlot.getHasStack()) {
        			itemstack = slot.getStack().copy();
        			playerSlot.putStack(itemstack);
        			slot.putStack(ItemStack.EMPTY);
        			playerSlot.onSlotChanged();
        			slot.onSlotChanged();
        		}
        	}
        }
        return itemstack;

        /*if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            itemstack = stackInSlot.copy();

            // If clicking on slot 0, try to move to slot 1
            if (index == 0) {
                Slot targetSlot = playerIn.inventoryContainer.getSlot(1);

                if (targetSlot != null && !targetSlot.getHasStack() && targetSlot.isItemValid(stackInSlot)) {
                    targetSlot.putStack(stackInSlot.splitStack(stackInSlot.getCount()));
                    slot.putStack(ItemStack.EMPTY);
                    targetSlot.onSlotChanged();
                    slot.onSlotChanged();
                }
            }
        }*/

        /*ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) { // Correct check: slot must contain an item
            ItemStack stackInSlot = slot.getStack();
            itemstack = stackInSlot.copy();

            if (index == 0) { // From necklace slot to player
                if (!this.mergeItemStack(stackInSlot, 1, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else { // From player inventory to necklace slot
                if (stackInSlot.getItem() != ModItems.OXONIUM_NECKLACE) {
                    return ItemStack.EMPTY;
                }

                if (!this.mergeItemStack(stackInSlot, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackInSlot.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            slot.onTake(playerIn, stackInSlot);
        }

        return itemstack;*/
    }

}
