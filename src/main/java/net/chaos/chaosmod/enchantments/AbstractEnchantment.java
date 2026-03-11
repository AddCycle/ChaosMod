package net.chaos.chaosmod.enchantments;

import net.chaos.chaosmod.init.ModEnchants;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public abstract class AbstractEnchantment extends Enchantment {

	protected AbstractEnchantment(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
		super(rarityIn, typeIn, slots);
		this.setName(name);
        this.setRegistryName(new ResourceLocation(Reference.MODID, name));
        
        ModEnchants.ENCHANTS.add(this);
	}
	
	@Override
    public abstract int getMaxLevel();

	@Override
    public abstract int getMinLevel();

    @Override
    public abstract int getMinEnchantability(int level);

    @Override
    public abstract int getMaxEnchantability(int level);
}