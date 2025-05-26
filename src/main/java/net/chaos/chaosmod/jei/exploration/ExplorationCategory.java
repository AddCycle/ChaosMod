package net.chaos.chaosmod.jei.exploration;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class ExplorationCategory implements IRecipeCategory<ExplorationWrapper>{
	public static final String UID = "chaosmod.exploration_category";
	public static int WIDTH = 150;
	public static int HEIGHT = 100;
	
	private final IDrawable background;

    public ExplorationCategory(IGuiHelper guiHelper) {
        // 150x100 as an example size; adjust as needed
        this.background = guiHelper.createBlankDrawable(WIDTH, HEIGHT);
        // this.background = guiHelper.createDrawable(new ResourceLocation(Reference.MODID, "textures/exploration/jei/test_bg.png"), 0, 0, 170, 130);
    }

	@Override
	public String getUid() {
		return UID;
	}

	@Override
	public String getTitle() {
		return "Exploration"; // TODO: add localization
	}

	@Override
	public String getModName() {
		return "ChaosMod";
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
    public void setRecipe(IRecipeLayout layout, ExplorationWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup stacks = layout.getItemStacks();

        // Arrange input/output positions manually
        List<List<ItemStack>> inputs = ingredients.getInputs(ItemStack.class);
        List<List<ItemStack>> outputs = ingredients.getOutputs(ItemStack.class);

        for (int i = 0; i < inputs.size(); i++) {
            int x = (i % 5) * 18;
            int y = (i / 5) * 18;
            stacks.init(i, true, x, y);
            stacks.set(i, inputs.get(i));
        }

        // Output at a defined position
        if (!outputs.isEmpty()) {
            stacks.init(inputs.size(), false, 120, 40);
            stacks.set(inputs.size(), outputs.get(0));
        }
    }

	@Override
	public void drawExtras(Minecraft minecraft) {
		GlStateManager.pushMatrix();
		
		GlStateManager.enableBlend();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableAlpha();
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);

		minecraft.getRenderItem().renderItemIntoGUI(new ItemStack(ModBlocks.BOSS_ALTAR), WIDTH / 2, HEIGHT / 2);
		
		GlStateManager.popMatrix();
		IRecipeCategory.super.drawExtras(minecraft);
	}
}
