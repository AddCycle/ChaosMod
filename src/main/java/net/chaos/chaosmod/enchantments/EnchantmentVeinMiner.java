package net.chaos.chaosmod.enchantments;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EnchantmentVeinMiner extends AbstractEnchantment {

	public EnchantmentVeinMiner(String name, Rarity rarityIn) {
		super(rarityIn, EnumEnchantmentType.DIGGER, new EntityEquipmentSlot[]{EntityEquipmentSlot.MAINHAND});
		this.setName(name);
        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
	}

	@Override
	public int getMinLevel() {
		return 1;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public int getMinEnchantability(int level) {
		return 15;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return 50;
	}

	@Override
	public boolean canApply(ItemStack stack) {
		return stack.getItem() instanceof ItemPickaxe;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		return stack.getItem() instanceof ItemPickaxe;
	}
}
