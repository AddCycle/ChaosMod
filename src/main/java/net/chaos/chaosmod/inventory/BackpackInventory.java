package net.chaos.chaosmod.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import util.Reference;

public class BackpackInventory extends ItemStackHandler {
	private final ItemStack stack;
	private final String compoundTag = Reference.MODID + "_allemanite_backpack_inv";

    public BackpackInventory(ItemStack stack) {
        super(27); // 27 slots
        this.stack = stack;
        if (stack.hasTagCompound()) {
            deserializeNBT(stack.getTagCompound().getCompoundTag(compoundTag));
        }
    }

    @Override
    protected void onContentsChanged(int slot) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) tag = new NBTTagCompound();
        tag.setTag(compoundTag, serializeNBT());
        stack.setTagCompound(tag);
    }
    
    public void saveToNBT() {
    	NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) tag = new NBTTagCompound();
        tag.setTag(compoundTag, serializeNBT());
        stack.setTagCompound(tag);
    }

	public int getSizeInventory() {
		return 27;
	}

}
