package net.chaos.chaosmod.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.chaos.chaosmod.jei.oxonium_furnace.OxoniumFurnaceCategory;
import net.chaos.chaosmod.jei.oxonium_furnace.OxoniumFurnaceMaker;

@JEIPlugin
public class JEIChaosPlugin implements IModPlugin {

	@Override
    public void register(IModRegistry registry) {
        // You can register your recipes, categories, and catalysts here
        // registry.addRecipes(ExampleRecipeMaker.getRecipes(), ExampleRecipeCategory.UID);
        registry.addRecipes(ExampleMultipleRecipeMaker.getRecipes(), ExampleMultipleRecipeCategory.UID);
        registry.addRecipes(OxoniumFurnaceMaker.getRecipes(), OxoniumFurnaceCategory.UID);

        // registry.addRecipeCategories(new ExampleRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ExampleMultipleRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new OxoniumFurnaceCategory(registry.getJeiHelpers().getGuiHelper()));
    }
}