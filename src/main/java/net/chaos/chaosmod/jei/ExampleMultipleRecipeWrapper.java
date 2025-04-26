package net.chaos.chaosmod.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class ExampleMultipleRecipeWrapper implements IRecipeWrapper {
	private List<ItemStack> inputs = new ArrayList<>();
	private final ItemStack output;
	
	public ExampleMultipleRecipeWrapper(List<ItemStack> inputs, ItemStack output) {
		this.inputs = inputs;
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(VanillaTypes.ITEM, inputs);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}

}
