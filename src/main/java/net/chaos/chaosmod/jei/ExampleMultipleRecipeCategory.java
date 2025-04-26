package net.chaos.chaosmod.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class ExampleMultipleRecipeCategory implements IRecipeCategory<ExampleMultipleRecipeWrapper>{
	public static final String UID = "chaosmod.general_category_multiple";

    private final IDrawable background;
    private final String localizedName;

    public ExampleMultipleRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(new ResourceLocation("minecraft", "textures/gui/container/crafting_table.png"), 29, 16, 116, 54);
        this.localizedName = new TextComponentTranslation("chaosmod.jei.crafting_category")
        		.setStyle(new Style().setBold(true).setColor(TextFormatting.BLUE)).getFormattedText();
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ExampleMultipleRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        boolean flag = false;
        int j = 0;
        for (int i = 0; i < 9; i++) { // 9x9 crafting table
        	if (i % 3 == 0 && flag) {
        		j += 18;
        	}
        	stacks.init(i, true, (i % 3) * 18, j); // Inputs
        	flag = true;
        }
        stacks.init(9, false, 94, 18); // Output

        stacks.set(ingredients);
    }

	@Override
	public String getModName() {
		return "ChaosMod";
	}
}
