package net.chaos.chaosmod.jei;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ExampleMultipleRecipeMaker {
	public static List<ExampleMultipleRecipeWrapper> getRecipes() {
        List<ExampleMultipleRecipeWrapper> recipes = new ArrayList<>();
        List<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(ModItems.ALLEMANITE_INGOT));
        inputs.add(new ItemStack(ModItems.ALLEMANITE_INGOT));
        inputs.add(new ItemStack(Items.IRON_INGOT));
        inputs.add(new ItemStack(ModItems.ALLEMANITE_INGOT));
        inputs.add(new ItemStack(ModItems.ALLEMANITE_INGOT));
        inputs.add(new ItemStack(ModItems.ALLEMANITE_INGOT));
        inputs.add(new ItemStack(ModItems.ALLEMANITE_INGOT));
        recipes.add(new ExampleMultipleRecipeWrapper(inputs, new ItemStack(ModItems.ALLEMANITE_EXTINGUISHER)));
        return recipes;
	}

}
