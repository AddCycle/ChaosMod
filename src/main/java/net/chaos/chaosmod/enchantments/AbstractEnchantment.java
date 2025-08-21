package net.chaos.chaosmod.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public abstract class AbstractEnchantment extends Enchantment {

	protected AbstractEnchantment(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
		super(rarityIn, typeIn, slots);
	}
	
	@Override
    public abstract int getMaxLevel();

	@Override
    public abstract int getMinLevel();

    @Override
    public abstract int getMinEnchantability(int level);

    @Override
    public abstract int getMaxEnchantability(int level);

    // Optional: restrict to certain tools
    @Override
    public abstract boolean canApply(ItemStack stack);
    
    @Override
    public abstract boolean canApplyAtEnchantingTable(ItemStack stack);
    
    @Override
    protected boolean canApplyTogether(Enchantment ench) {
    	return ench != this;
    }

}
