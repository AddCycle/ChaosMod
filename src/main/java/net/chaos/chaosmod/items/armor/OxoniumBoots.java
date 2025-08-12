package net.chaos.chaosmod.items.armor;

import net.minecraft.inventory.EntityEquipmentSlot;

public class OxoniumBoots extends ArmorBase {

	public OxoniumBoots(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	@Override
	public boolean canItemEditBlocks() {
		return false;
	}

}
