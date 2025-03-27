package util.compat.jei.oxonium_furnace;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Table;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.chaos.chaosmod.recipies.OxoniumFurnaceRecipes;
import net.minecraft.item.ItemStack;

public class OxoniumFurnaceRecipeMaker {
	// FIXME !!!!
	public static List<OxoniumFurnaceRecipe> guiRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
		OxoniumFurnaceRecipes instance = OxoniumFurnaceRecipes.instance();
		Map<ItemStack, ItemStack> recipes = instance.getSmeltingList();
		List<OxoniumFurnaceRecipe> jeiRecipe = Lists.newArrayList();
		
		for (Entry<ItemStack, ItemStack> ent : recipes.entrySet()) {
			ItemStack input = ent.getKey();
			ItemStack output = ent.getValue();
			OxoniumFurnaceRecipe recipe = new OxoniumFurnaceRecipe(input, output);
			jeiRecipe.add(recipe);
		}

		return jeiRecipe;
	}
	

}
