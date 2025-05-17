package net.chaos.chaosmod.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ForgeInterfaceContainer extends Container {
	private final IInventory tileEntity;

    public ForgeInterfaceContainer(InventoryPlayer playerInventory, IInventory tileEntity) {
        this.tileEntity = tileEntity;

        // 4 in a row
        for (int i = 0; i < 3; ++i) {
        	this.addSlotToContainer(new Slot(tileEntity, i, 15 + i * 18, 34));
        }
        this.addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, tileEntity, 3, 116, 35));

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
    
    @Override
    public void addListener(IContainerListener listener) {
        super.addListener(listener);
        listener.sendAllWindowProperties(this, tileEntity);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (IContainerListener listener : listeners) {
            listener.sendWindowProperty(this, 0, tileEntity.getField(0));
            listener.sendWindowProperty(this, 1, tileEntity.getField(1));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        tileEntity.setField(id, data);
    }

}
