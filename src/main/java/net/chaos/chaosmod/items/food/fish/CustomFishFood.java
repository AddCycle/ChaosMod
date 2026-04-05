package net.chaos.chaosmod.items.food.fish;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import proxy.IItemModel;

// do it more like a cryofreeze fish
public class CustomFishFood extends ItemFood implements IItemModel {

	public CustomFishFood(String name, int amount) {
		super(amount, true);
		this.setHasSubtypes(true);

		setUnlocalizedName(name);
		setRegistryName(name);
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 16; // vanilla: 32
	}
}