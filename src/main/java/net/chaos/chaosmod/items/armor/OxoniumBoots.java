package net.chaos.chaosmod.items.armor;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class OxoniumBoots extends ArmorBase {

	public OxoniumBoots(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("So strong that you need to run...");
		tooltip.add(String.format("[%s] Doesn't break crops stepping on them", "Farmer's bless"));
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	@Override
	public boolean canItemEditBlocks() {
		return false;
	}

}