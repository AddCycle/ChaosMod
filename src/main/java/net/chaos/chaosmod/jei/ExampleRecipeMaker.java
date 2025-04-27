package net.chaos.chaosmod.jei;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ExampleRecipeMaker {
	public static List<ExampleRecipeWrapper> getRecipes() {
        List<ExampleRecipeWrapper> recipes = new ArrayList<>();
        // add here the recipes so I think it's one wrapper for one category
        recipes.add(new ExampleRecipeWrapper(new ItemStack(ModBlocks.OXONIUM_BLOCK), new ItemStack(ModItems.OXONIUM_INGOT, 9)));
        recipes.add(new ExampleRecipeWrapper(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GOLD_INGOT)));
        return recipes;
    }

}
