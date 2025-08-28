package net.chaos.chaosmod.potion;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModPotionTypes;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

public class BrewingRecipeViking implements IBrewingRecipe {

	@Override
	public boolean isInput(ItemStack input) {
	    return input.getItem() == Items.POTIONITEM
	        && PotionUtils.getPotionFromItem(input) == PotionTypes.AWKWARD;
	}

	@Override
	public boolean isIngredient(ItemStack ingredient) {
	    return ingredient.getItem() == Item.getItemFromBlock(ModBlocks.CUSTOM_FLOWER)
	        && ingredient.getMetadata() == 0;
	}

	@Override
	public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
	    if (isInput(input) && isIngredient(ingredient)) {
	        return PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM),
	            ModPotionTypes.VIKING_FRIEND_TYPE);
	    }
	    return ItemStack.EMPTY;
	}

}
