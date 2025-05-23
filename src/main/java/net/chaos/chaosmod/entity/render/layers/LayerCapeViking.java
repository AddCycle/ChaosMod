package net.chaos.chaosmod.entity.render.layers;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.entity.EntityViking;
import net.chaos.chaosmod.entity.model.ModelViking;
import net.chaos.chaosmod.entity.render.EntityVikingRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class LayerCapeViking implements LayerRenderer<EntityViking> {
    private final EntityVikingRenderer renderViking;

    public LayerCapeViking(EntityVikingRenderer renderViking) {
        this.renderViking = renderViking;
    }

    @Override
    public void doRenderLayer(EntityViking entity, float limbSwing, float limbSwingAmount, float partialTicks,
                              float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!entity.isInvisible()) {
        	/*
        	 * Vanilla one :
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationCape());
                GlStateManager.pushMatrix();
                GlStateManager.translate(0.0F, 0.0F, 0.125F);
                double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * (double)partialTicks - (entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * (double)partialTicks);
                double d1 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * (double)partialTicks - (entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * (double)partialTicks);
                double d2 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * (double)partialTicks - (entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * (double)partialTicks);
                float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
                double d3 = (double)MathHelper.sin(f * 0.017453292F);
                double d4 = (double)(-MathHelper.cos(f * 0.017453292F));
                float f1 = (float)d1 * 10.0F;
                f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
                float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
                float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;

                if (f2 < 0.0F)
                {
                    f2 = 0.0F;
                }

                float f4 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
                f1 = f1 + MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

                if (entitylivingbaseIn.isSneaking())
                {
                    f1 += 25.0F;
                }

                GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                this.playerRenderer.getMainModel().renderCape(0.0625F);
                GlStateManager.popMatrix();
        	 */
            GlStateManager.pushMatrix();

            // Position the cape on the back
            ((ModelViking) this.renderViking.getMainModel()).bipedCape.postRender(0.0625F);
            GlStateManager.translate(0.0F, 0.0F, 0.125F); // Adjust as needed
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("chaosmod:textures/entity/viking_cape.png"));

            // Simple flat cape model (or replace with your own ModelRenderer)
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.getBuffer();
            buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

            float w = 0.5F, h = 1.0F;

            buffer.pos(-w, 0, 0).tex(0, 0).endVertex();
            buffer.pos(w, 0, 0).tex(1, 0).endVertex();
            buffer.pos(w, h, 0).tex(1, 1).endVertex();
            buffer.pos(-w, h, 0).tex(0, 1).endVertex();

            tessellator.draw();
            GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}