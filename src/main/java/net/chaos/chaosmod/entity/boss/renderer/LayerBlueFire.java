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
	int red;
	int green;
	int blue;

    private final RenderLivingBase<T> renderer;

    public LayerBlueFire(RenderLivingBase<T> renderer, int red, int green, int blue) {
        this.renderer = renderer;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @Override
    public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount,
                              float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
