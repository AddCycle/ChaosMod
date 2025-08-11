package net.chaos.chaosmod.potion;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModPotionTypes;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class BrewingRecipeVikingPotion implements IBrewingRecipe {

	@Override
	public boolean isInput(ItemStack input) {
		ItemStack accepted = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.AWKWARD);
		return input == accepted;
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
		ItemStack accepted = new ItemStack(ModBlocks.CUSTOM_FLOWER, 1, 1);
		return ingredient == accepted;
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
		ItemStack res = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), ModPotionTypes.VIKING_FRIEND_TYPE);
		return res;
	}

}
