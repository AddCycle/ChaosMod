package net.chaos.chaosmod.entity.inventory;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;

public class SimpleEntityInventory extends InventoryBasic {
	private final EntityLiving entity;
	private static final EntityEquipmentSlot[] ARMOR_SLOTS = {
	        EntityEquipmentSlot.FEET,
	        EntityEquipmentSlot.LEGS, 
	        EntityEquipmentSlot.CHEST,
	        EntityEquipmentSlot.HEAD
	    };

	public SimpleEntityInventory(EntityLiving entity, List<ItemStack> inventoryContents, int slotCount) {
		super(entity.getName(), true, slotCount);
		this.entity = entity;
		for (int i = 0; i < slotCount; i++) {
			setInventorySlotContents(i, inventoryContents.get(i));
		}
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		super.setInventorySlotContents(index, stack);
		
		entity.setItemStackToSlot(ARMOR_SLOTS[index], stack);
	}
}