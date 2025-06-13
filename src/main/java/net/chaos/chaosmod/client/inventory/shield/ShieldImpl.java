package net.chaos.chaosmod.client.inventory.shield;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ShieldImpl implements IShield {
	private ItemStack shield = ItemStack.EMPTY;

    @Override
    public ItemStack getShieldItem() {
        return shield;
    }

    @Override
    public void setShieldItem(ItemStack stack) {
    	// Main.network.sendToAll(new PacketAccessorySync(player, stack));
        this.shield = stack;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        if (!shield.isEmpty())
            tag.setTag("Shield", shield.writeToNBT(new NBTTagCompound()));
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("Shield")) shield = new ItemStack(nbt.getCompoundTag("Shield")); else shield = ItemStack.EMPTY;
    }
}