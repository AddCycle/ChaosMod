package util.tilentity;

import net.minecraft.item.ItemStack;

public interface ISimpleInventory {
	public int getSize();
	public ItemStack getItem(int i);
	public void clear();
}
