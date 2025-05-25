package net.chaos.chaosmod.entity.render.layers;

import net.chaos.chaosmod.entity.EntityViking;
import net.chaos.chaosmod.entity.model.ModelViking;
import net.chaos.chaosmod.entity.render.EntityVikingRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import util.Reference;

public class LayerCapeViking implements LayerRenderer<EntityViking> {
    private final EntityVikingRenderer renderViking;
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/viking_cape.png");
	private static final ResourceLocation TEXTURE_ANGER = new ResourceLocation(Reference.MODID, "textures/entity/viking_angry_cape.png");

    public LayerCapeViking(EntityVikingRenderer renderViking) {
        this.renderViking = renderViking;
    }

    @Override
    public void doRenderLayer(EntityViking entity, float limbSwing, float limbSwingAmount, float partialTicks,
                              float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (!entity.isInvisible()) {
        	/* Vanilla one */
        	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        	this.renderViking.bindTexture(entity.angry ? TEXTURE_ANGER : TEXTURE);
        	GlStateManager.pushMatrix();
        	GlStateManager.translate(0.0F, 0.0F, 0.125F);
        	double d0 = entity.prevChasingPosX + (entity.chasingPosX - entity.prevChasingPosX) * (double)partialTicks - (entity.prevPosX + (entity.posX - entity.prevPosX) * (double)partialTicks);
        	double d1 = entity.prevChasingPosY + (entity.chasingPosY - entity.prevChasingPosY) * (double)partialTicks - (entity.prevPosY + (entity.posY - entity.prevPosY) * (double)partialTicks);
        	double d2 = entity.prevChasingPosZ + (entity.chasingPosZ - entity.prevChasingPosZ) * (double)partialTicks - (entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)partialTicks);
        	float f = entity.prevRenderYawOffset + (entity.renderYawOffset - entity.prevRenderYawOffset) * partialTicks;
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

        	float f4 = entity.prevCameraYaw + (entity.cameraYaw - entity.prevCameraYaw) * partialTicks;
        	f1 = f1 + MathHelper.sin((entity.prevDistanceWalkedModified + (entity.distanceWalkedModified - entity.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;

        	if (entity.isSneaking())
        	{
        		f1 += 25.0F;
        	}

        	GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
        	GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
        	GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
        	GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        	((ModelViking) this.renderViking.getMainModel()).renderCape(0.0625F);
        	GlStateManager.popMatrix();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}