package net.chaos.chaosmod.client.inventory.shield;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IShield extends INBTSerializable<NBTTagCompound> {
	ItemStack getShieldItem();
	void setShieldItem(ItemStack stack);
}