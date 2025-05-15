package net.chaos.chaosmod.client.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAccessory extends INBTSerializable<NBTTagCompound> {
	ItemStack getAccessoryItem();
	void setAccessoryItem(ItemStack stack);
}