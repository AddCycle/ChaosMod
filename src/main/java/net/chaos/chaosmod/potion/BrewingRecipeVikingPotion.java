package net.chaos.chaosmod.potion;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModPotionTypes;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class BrewingRecipeVikingPotion implements IBrewingRecipe {

	private static final ItemStack ACCEPTED_INPUT = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD);
	private static final ItemStack ACCEPTED_INGREDIENT = new ItemStack(ModBlocks.CUSTOM_FLOWER, 1, 1);

	@Override
	public boolean isInput(ItemStack input) {
		// Use areItemStacksEqual to compare input including NBT (important for potions)
		return ItemStack.areItemStacksEqual(input, ACCEPTED_INPUT);
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		// For ingredient, metadata matters but likely no NBT, so areItemsEqual is fine
		return ItemStack.areItemsEqual(ingredient, ACCEPTED_INGREDIENT);
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		// Return a new ItemStack of the potion with your custom type
		return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), ModPotionTypes.VIKING_FRIEND_TYPE);
	}
}
