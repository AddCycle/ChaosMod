package util.compat.jei;

import java.util.IllegalFormatException;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.chaos.chaosmod.client.gui.inventory.OxoniumFurnaceGui;
import net.chaos.chaosmod.inventory.OxoniumFurnaceContainer;
import net.minecraft.util.text.TextComponentTranslation;
import util.compat.jei.oxonium_furnace.OxoniumFurnaceRecipeCategory;
import util.compat.jei.oxonium_furnace.OxoniumFurnaceRecipeMaker;

public class JEICompatibility implements IModPlugin {

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		/*final IJeiHelpers helpers = registry.getJeiHelpers();
		final IGuiHelper gui = helpers.getGuiHelper();
		
		registry.addRecipeCategories(new OxoniumFurnaceRecipeCategory(gui));*/
	}

	@Override
	public void register(IModRegistry registry) {
		/*final IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
		final IJeiHelpers helpers = registry.getJeiHelpers();
		
		IRecipeTransferRegistry recipeTransfer = registry.getRecipeTransferRegistry();
		
		registry.addRecipes(OxoniumFurnaceRecipeMaker.guiRecipes(helpers), RecipeCategories.OXONIUM_FURNACE);
		registry.addRecipeClickArea(OxoniumFurnaceGui.class, 110, 0, 50, 50, RecipeCategories.OXONIUM_FURNACE);
		recipeTransfer.addRecipeTransferHandler(OxoniumFurnaceContainer.class, RecipeCategories.OXONIUM_FURNACE, 0, 1, 3, 36);*/
	}
	
	public static String translateToLocal(String key) {
		return new TextComponentTranslation(key).getUnformattedText();
	}
	
	public static String translateToLocalFormatted(String key, Object... format) {
		String s = translateToLocal(key);
		try {
			return String.format(s, format);
		} catch (IllegalFormatException e) {
			System.err.println("Erreur de format JEI : JEICompatibility" + e);
			return "Format error";
		}
	}
}
