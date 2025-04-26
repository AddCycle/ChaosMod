package net.chaos.chaosmod.jei.oxonium_furnace;

import java.util.Collections;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class OxoniumFurnaceWrapper implements IRecipeWrapper {
	private final ItemStack input;
    private final ItemStack output;

    public OxoniumFurnaceWrapper(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, Collections.singletonList(input));
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }

}
