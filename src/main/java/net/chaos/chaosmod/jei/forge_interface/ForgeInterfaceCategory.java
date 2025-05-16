package net.chaos.chaosmod.jei.forge_interface;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class ForgeInterfaceCategory implements IRecipeCategory<ForgeInterfaceRecipeWrapper>{
	public static final String UID = "chaosmod.forge_interface_recipes";
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawableAnimated arrow;
    private final IDrawableAnimated hammer;

    public ForgeInterfaceCategory(IGuiHelper guiHelper) {
        // int offsetX = 29;
        int offsetX = 13;
        // int offsetY = 16;
        int offsetY = 13;
        background = guiHelper.createDrawable(
            new ResourceLocation(Reference.MODID, "textures/gui/container/forge_interface.png"),
            offsetX, offsetY, 126, 58
        );

        icon = guiHelper.createDrawableIngredient(new ItemStack(ModBlocks.FORGE_INTERFACE_BLOCK));

        IDrawableStatic arrowStatic = guiHelper.createDrawable(
            new ResourceLocation(Reference.MODID, "textures/gui/container/forge_interface.png"),
            176, 14, 24, 17
        );
        IDrawableStatic hammerStatic = guiHelper.createDrawable(
            new ResourceLocation(Reference.MODID, "textures/gui/container/forge_interface.png"),
            176, 30, 18, 18
        );

        int fabricationTime = 200;
        arrow = guiHelper.createAnimatedDrawable(arrowStatic, fabricationTime, IDrawableAnimated.StartDirection.LEFT, false);
        hammer = guiHelper.createAnimatedDrawable(hammerStatic, fabricationTime, IDrawableAnimated.StartDirection.BOTTOM, false);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return "Chaos Forge";
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
        int offsetX = 13;
        int offsetY = 13;
        arrow.draw(minecraft, 76 - offsetX, 34 - offsetY); // Adjust X/Y to fit nicely
        hammer.draw(minecraft, 14 - offsetX, 15 - offsetY);
        hammer.draw(minecraft, 32 - offsetX, 51 - offsetY);
        hammer.draw(minecraft, 50 - offsetX, 15 - offsetY);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, ForgeInterfaceRecipeWrapper recipe, IIngredients ingredients) {
        int x = 13; // offsetX
        int y = 13; // offsetY
        recipeLayout.getItemStacks().init(0, true, 14 - x, 33 - y);   // Inputs
        recipeLayout.getItemStacks().init(1, true, 14 - x + 18, 33 - y);   // Inputs
        recipeLayout.getItemStacks().init(2, true, 14 - x + 18 * 2, 33 - y);   // Inputs
        recipeLayout.getItemStacks().init(3, false, 102, 33 - y); // Output
        recipeLayout.getItemStacks().set(ingredients);
    }

	@Override
	public String getModName() {
		return "ChaosMod";
	}

}
