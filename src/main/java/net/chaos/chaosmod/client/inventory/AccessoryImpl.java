package net.chaos.chaosmod.client.inventory;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.network.PacketAccessorySync;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class AccessoryImpl implements IAccessory {
	private ItemStack accessory = ItemStack.EMPTY;

    @Override
    public ItemStack getAccessoryItem() {
        return accessory;
    }

    @Override
    public void setAccessoryItem(ItemStack stack) {
    	// Main.network.sendToAll(new PacketAccessorySync(player, stack));
        this.accessory = stack;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        if (!accessory.isEmpty())
            tag.setTag("Accessory", accessory.writeToNBT(new NBTTagCompound()));
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("Accessory"))
            accessory = new ItemStack(nbt.getCompoundTag("Accessory"));
        else
            accessory = ItemStack.EMPTY;
    }
}