package net.chaos.chaosmod.inventory;

import net.chaos.chaosmod.items.special.PlayerInventoryBaseItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class BackpackContainer extends Container {
	public final BackpackInventory inventory;

    public BackpackContainer(InventoryPlayer playerInv, ItemStack stack) {
        this.inventory = new BackpackInventory(stack);

        // Backpack inventory (3 rows of 9)
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlotToContainer(new SlotItemHandler(inventory, col + row * 9, 8 + col * 18, 18 + row * 18) {
                	@Override
                	public boolean isItemValid(ItemStack stack) {
                		return !(stack.getItem() instanceof PlayerInventoryBaseItem);
                	};
                } );
            }
        }

        // Player inventory slots
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlotToContainer(new Slot(playerInv, col + row * 9 + 9, 8 + col * 18, 85 + row * 18));
            }
        }

        // Hotbar
        for (int i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(playerInv, i, 8 + i * 18, 143));
        }
    }
    
    @Override
    public void onContainerClosed(EntityPlayer playerIn) {
        super.onContainerClosed(playerIn);
        inventory.saveToNBT();
    }

	@Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		// TODO Auto-generated method stub
		// return super.transferStackInSlot(playerIn, index);
		return ItemStack.EMPTY; // prevents from crashing TODO + FIXME
	}

}
