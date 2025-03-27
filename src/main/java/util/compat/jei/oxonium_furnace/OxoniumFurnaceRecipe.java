package util.compat.jei.oxonium_furnace;

import java.awt.Color;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.chaos.chaosmod.recipies.OxoniumFurnaceRecipes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import util.compat.jei.JEICompatibility;

public class OxoniumFurnaceRecipe implements IRecipeWrapper {
	private final ItemStack input;
	private final ItemStack output;
	
	public OxoniumFurnaceRecipe(ItemStack input, ItemStack output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		// ingredients.setInputs(ItemStack.class, inputs);
		ingredients.setInput(ItemStack.class, input);
		ingredients.setOutput(ItemStack.class, output);
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		OxoniumFurnaceRecipes recipes = OxoniumFurnaceRecipes.instance();
		float experience = recipes.getSmeltingExperience(output);
		
		if (experience > 0) {
			String experienceString = JEICompatibility.translateToLocalFormatted("gui.jei.category.smelting.experience", experience);
			FontRenderer renderer = minecraft.fontRenderer;
			int stringWidth = renderer.getStringWidth(experienceString);
			renderer.drawString(experienceString, recipeWidth - stringWidth, 0, Color.GRAY.getRGB());
		}
	}

}
