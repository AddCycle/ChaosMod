package net.chaos.chaosmod.client.renderer.tileentity;

import net.chaos.chaosmod.client.model.ModelTrophyBase;
import net.chaos.chaosmod.tileentity.TileEntityTrophyBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class TrophyTESR extends TileEntitySpecialRenderer<TileEntityTrophyBase> {
	private static final ModelTrophyBase model = new ModelTrophyBase();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/tileentity/trophy_base.png");

    @Override
    public void render(TileEntityTrophyBase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
        GlStateManager.rotate(180F, 0F, 0F, 1F);

        bindTexture(TEXTURE);
        model.render(0.0625f);
        
        ItemStack stack = te.getStackInSlot(0);
        if (!stack.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(180F, 0F, 0F, 1F);

            GlStateManager.translate(0.0, -0.20, 0.0);

            GlStateManager.rotate((te.getWorld().getTotalWorldTime() % 360) * 4F, 0F, 1F, 0F);

            // Slight scale-down (optional)
            GlStateManager.scale(0.3F, 0.3F, 0.3F);

            // Lighting
            RenderHelper.enableStandardItemLighting();

            // Render item as fixed object
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);

            RenderHelper.disableStandardItemLighting();
            GlStateManager.popMatrix();
        }

        GlStateManager.popMatrix();
    }
}
