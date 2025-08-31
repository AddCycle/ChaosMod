package net.chaos.chaosmod.market;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class MarketOffer {
	private ItemStack item;
	private int price;
	
	public MarketOffer(ItemStack item, int price) {
		this.item = item;
		this.price = price;
	}
	
	public ItemStack getItem() {
		return item.copy();
	}
	
	public int getPrice() {
		return price;
	}
	
	public NBTTagCompound toNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("Item", item.serializeNBT());
		tag.setInteger("Price", price);
		return tag;
	}

	public static MarketOffer fromNBT(NBTTagCompound nbt) {
		ItemStack stack = new ItemStack(nbt.getCompoundTag("Item"));
		int price = nbt.getInteger("Price");
		return new MarketOffer(stack, price);
	}
}
