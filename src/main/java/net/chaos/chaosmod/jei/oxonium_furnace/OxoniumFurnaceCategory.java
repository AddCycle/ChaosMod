package net.chaos.chaosmod.jei.oxonium_furnace;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class OxoniumFurnaceCategory implements IRecipeCategory<OxoniumFurnaceWrapper>{
	public static final String UID = "chaosmod.oxonium_furnace_recipes";
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawableAnimated flame;

    public OxoniumFurnaceCategory(IGuiHelper guiHelper) {
        int offsetX = 29;
        int offsetY = 16;
        background = guiHelper.createDrawable(
            new ResourceLocation(Reference.MODID, "textures/gui/container/furnace.png"),
            offsetX, offsetY, 116, 54
        );

        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.OXONIUM_FURNACE));

        IDrawableStatic arrowStatic = guiHelper.createDrawable(
            new ResourceLocation(Reference.MODID, "textures/gui/container/furnace.png"),
            176, 14, 24, 17
        );
        IDrawableStatic flameStatic = guiHelper.createDrawable(
            new ResourceLocation(Reference.MODID, "textures/gui/container/furnace.png"),
            176, 0, 14, 14
        );

        int cookTime = TileEntityOxoniumFurnace.getCookTime(null);
        arrow = guiHelper.createAnimatedDrawable(arrowStatic, cookTime, IDrawableAnimated.StartDirection.LEFT, false);
        flame = guiHelper.createAnimatedDrawable(flameStatic, cookTime, IDrawableAnimated.StartDirection.TOP, true);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Chaos Smelting";
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrow.draw(minecraft, 50, 18); // Adjust X/Y to fit nicely
        flame.draw(minecraft, 27, 20);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, OxoniumFurnaceWrapper recipe, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 26, 0);   // Input
        recipeLayout.getItemStacks().init(1, false, 86, 18); // Output
        recipeLayout.getItemStacks().set(ingredients);
    }

	@Override
	public String getModName() {
		return "ChaosMod";
	}

}
