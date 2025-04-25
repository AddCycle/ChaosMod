package net.chaos.chaosmod.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class ExampleRecipeCategory implements IRecipeCategory<ExampleRecipeWrapper> {
	public static final String UID = "chaosmod.general_category";

    private final IDrawable background;
    private final String localizedName;

    public ExampleRecipeCategory(IGuiHelper guiHelper) {
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
    public void setRecipe(IRecipeLayout recipeLayout, ExampleRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = recipeLayout.getItemStacks();
        stacks.init(0, true, 0, 18);  // Input
        stacks.init(1, false, 94, 18); // Output

        stacks.set(ingredients);
    }

	@Override
	public String getModName() {
		return "ChaosMod";
	}
}
