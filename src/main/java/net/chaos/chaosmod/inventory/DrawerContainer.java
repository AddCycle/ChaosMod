package net.chaos.chaosmod.inventory;

import net.chaos.chaosmod.tileentity.TileEntityDrawer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class DrawerContainer extends Container {

	public DrawerContainer(TileEntityDrawer drawer, EntityPlayer player) {
        // Drawer slot
        this.addSlotToContainer(new Slot(drawer, 0, 80, 35));

        // Player inventory
        for (int row = 0; row < 3; row++)
            for (int col = 0; col < 9; col++)
                this.addSlotToContainer(new Slot(player.inventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));

        for (int col = 0; col < 9; col++)
            this.addSlotToContainer(new Slot(player.inventory, col, 8 + col * 18, 142));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        Slot slot = this.inventorySlots.get(index);
        if (slot == null || !slot.getHasStack()) return ItemStack.EMPTY;

        ItemStack stack = slot.getStack();
        ItemStack copy = stack.copy();

        if (index == 0) {
            // From drawer to player inventory
            if (!mergeItemStack(stack, 1, inventorySlots.size(), true)) return ItemStack.EMPTY;
        } else {
            // From player inventory to drawer
            if (!mergeItemStack(stack, 0, 1, false)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.putStack(ItemStack.EMPTY);
        else slot.onSlotChanged();

        return copy;
    }
}