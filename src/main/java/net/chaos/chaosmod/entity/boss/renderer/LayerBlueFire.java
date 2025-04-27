package net.chaos.chaosmod.entity.boss.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class LayerBlueFire<T extends EntityLivingBase> implements LayerRenderer<T> {

    private static final ResourceLocation BLUE_FIRE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_0.png");
    private final RenderLivingBase<T> renderer;

    public LayerBlueFire(RenderLivingBase<T> renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount,
                              float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.isBurning()) {
            GlStateManager.depthMask(!entity.isInvisible());
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771); // SRC_ALPHA, ONE_MINUS_SRC_ALPHA
            GlStateManager.disableLighting();

            this.renderer.bindTexture(BLUE_FIRE_TEXTURE);

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();

            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 0.0F, 0.0F);
            float height = entity.height / 2.0F;
            float width = entity.width * 1.4F;

            for (int i = 0; i < 2; ++i) {
                GlStateManager.pushMatrix();
                float f = height * (1.0F - (float) i * 0.5F);
                GlStateManager.translate(0.0F, f, 0.0F);

                float minU = 0.0F;
                float maxU = 1.0F;
                float minV = 0.0F;
                float maxV = 1.0F;

                float offset = 0.5F * width;

                buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(-offset, 0.0D, -offset).tex(minU, maxV).endVertex();
                buffer.pos(-offset, 0.0D,  offset).tex(maxU, maxV).endVertex();
                buffer.pos( offset, 0.0D,  offset).tex(maxU, minV).endVertex();
                buffer.pos( offset, 0.0D, -offset).tex(minU, minV).endVertex();
                tessellator.draw();

                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();

            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
