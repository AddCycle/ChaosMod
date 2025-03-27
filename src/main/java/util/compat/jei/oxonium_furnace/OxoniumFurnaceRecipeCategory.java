package util.compat.jei.oxonium_furnace;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import util.Reference;
import util.compat.jei.RecipeCategories;

public class OxoniumFurnaceRecipeCategory extends AbstractFurnaceRecipeCategory<OxoniumFurnaceRecipe>{
	private final IDrawable background;
	private final String name;

	public OxoniumFurnaceRecipeCategory(IGuiHelper helper) {
		super(helper);
		background = helper.createDrawable(TEXTURES, 4, 12, 150, 60);
		name = "Oxonium Furnace";
	}
	
	@Override
	public IDrawable getBackground() {
		return background;
	}
	
	@Override
	public void drawExtras(Minecraft minecraft) {
		animatedFlame.draw(minecraft);
		animatedArrow.draw(minecraft);
	}

	@Override
	public String getTitle() {
		return name;
	}
	
	@Override
	public String getModName() {
		return Reference.NAME;
	}
	
	@Override
	public String getUid() {
		return RecipeCategories.OXONIUM_FURNACE;
	}
	
	@Override
	public void setRecipe(IRecipeLayout recipeLayout, OxoniumFurnaceRecipe recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
		stacks.init(input1, true, 21, 42);
		stacks.init(output, false, 76, 23);
		stacks.set(ingredients);
	}
}
