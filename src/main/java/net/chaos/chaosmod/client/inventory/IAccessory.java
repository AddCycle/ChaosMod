package net.chaos.chaosmod.client.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public interface IAccessory {
	ItemStack getAccessoryItem();
	void setAccessoryItem(ItemStack stack);
	public NBTTagCompound serializeNBT();
    public void deserializeNBT(NBTTagCompound nbt);
}