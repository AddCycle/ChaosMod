package net.chaos.chaosmod.inventory.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotStationMaterial extends Slot {

	public SlotStationMaterial(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return true; // FIXME : filter later-on based on recipes or a custom registry
	}

}
