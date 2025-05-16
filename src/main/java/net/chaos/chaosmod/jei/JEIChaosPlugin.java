package net.chaos.chaosmod.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.jei.forge_interface.ForgeInterfaceCategory;
import net.chaos.chaosmod.jei.forge_interface.ForgeInterfaceRecipeMaker;
import net.chaos.chaosmod.jei.oxonium_furnace.OxoniumFurnaceCategory;
import net.chaos.chaosmod.jei.oxonium_furnace.OxoniumFurnaceMaker;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIChaosPlugin implements IModPlugin {

	@Override
    public void register(IModRegistry registry) {
        // You can register your recipes, categories, and catalysts here
        registry.addRecipes(ExampleMultipleRecipeMaker.getRecipes(), ExampleMultipleRecipeCategory.UID);
        registry.addRecipes(OxoniumFurnaceMaker.getRecipes(), OxoniumFurnaceCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.OXONIUM_FURNACE), OxoniumFurnaceCategory.UID);
        registry.addRecipes(ForgeInterfaceRecipeMaker.getRecipes(), ForgeInterfaceCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.FORGE_INTERFACE_BLOCK), ForgeInterfaceCategory.UID);
    }
	
	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ExampleMultipleRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new OxoniumFurnaceCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new ForgeInterfaceCategory(registry.getJeiHelpers().getGuiHelper()));
	}
}