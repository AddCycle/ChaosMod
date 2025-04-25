package net.chaos.chaosmod.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class JEIChaosPlugin implements IModPlugin {

	@Override
    public void register(IModRegistry registry) {
        // You can register your recipes, categories, and catalysts here
        registry.addRecipes(ExampleRecipeMaker.getRecipes(), ExampleRecipeCategory.UID);

        registry.addRecipeCategories(new ExampleRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }
}