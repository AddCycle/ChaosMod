package net.chaos.chaosmod.entity.projectile.render;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EntitySmallBlueFireballRenderer extends Render<EntitySmallBlueFireball> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/projectile/blue_fireball.png");
	private static final ResourceLocation BLUE_FIRE_LAYER0 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_0.png");
	private static final ResourceLocation BLUE_FIRE_LAYER1 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_1.png");

	public EntitySmallBlueFireballRenderer(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySmallBlueFireball entity) {
		return TEXTURE;
	}
	
	@Override
	public void doRender(EntitySmallBlueFireball entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);
        this.bindTexture(TEXTURE);
        
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(0.5F, 0.5F, 0.5F); // Size of the fireball
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        
        float f = 1.0F;
        float f1 = 0.0F;
        float f2 = 1.0F;
        
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(-0.5, -0.5, 0.0).tex(0.0, 1.0).endVertex();
        bufferbuilder.pos(0.5, -0.5, 0.0).tex(1.0, 1.0).endVertex();
        bufferbuilder.pos(0.5, 0.5, 0.0).tex(1.0, 0.0).endVertex();
        bufferbuilder.pos(-0.5, 0.5, 0.0).tex(0.0, 0.0).endVertex();
        tessellator.draw();
        
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
		renderFireballOnFireCustom(entityIn, x, y, z, partialTicks);
	}
	
	private void renderFireballOnFireCustom(Entity entity, double x, double y, double z, float partialTicks)
	{
	    if (entity instanceof EntitySmallBlueFireball) {
	        EntitySmallBlueFireball fireball = (EntitySmallBlueFireball) entity;
	        
	        // Disable lighting to correctly render the fire effect
	        GlStateManager.disableLighting();
	        GlStateManager.pushMatrix();
	        GlStateManager.translate((float) x, (float) y, (float) z);

	        // Scale the fireball size (based on the fireball's size)
	        float scale = entity.width * 1.4F;
	        GlStateManager.scale(scale, scale, scale);

	        Tessellator tessellator = Tessellator.getInstance();
	        BufferBuilder bufferbuilder = tessellator.getBuffer();

	        // Dimensions of the fireball rendering area
	        float width = 0.5F;
	        float depth = 0.0F;
	        float height = entity.height / scale;
	        float yOffset = (float) (entity.posY - entity.getEntityBoundingBox().minY);

	        // Rotate the fireball to match the player's view
	        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	        GlStateManager.translate(0.0F, 0.0F, -0.3F + (float)((int)height) * 0.02F);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

	        // Animation setup for blue fire layers
	        final int totalFrames = 32; // Texture height (512px), 32 frames
	        final float frameHeight = 1.0F / totalFrames;
	        float animationSpeed = 2.0F; // Speed of animation

	        int ageInTicks = entity.ticksExisted;
	        float frameFloat = (ageInTicks + partialTicks) * animationSpeed;
	        int currentFrame = (int) frameFloat % totalFrames;

	        while (height > 0.0F) {
	            // Alternate between two layers for flickering
	            ResourceLocation currentTexture = ((ageInTicks / 2) % 2 == 0) ? BLUE_FIRE_LAYER0 : BLUE_FIRE_LAYER1;
	            Minecraft.getMinecraft().getTextureManager().bindTexture(currentTexture);

	            float uMin = 0.0F;
	            float uMax = 1.0F;
	            float vMin = frameHeight * currentFrame;
	            float vMax = vMin + frameHeight;

	            // Render the fire layer with the appropriate animation frame
	            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
	            bufferbuilder.pos((double)(width), (double)(0.0F - yOffset), (double)depth).tex((double)uMax, (double)vMax).endVertex();
	            bufferbuilder.pos((double)(-width), (double)(0.0F - yOffset), (double)depth).tex((double)uMin, (double)vMax).endVertex();
	            bufferbuilder.pos((double)(-width), (double)(1.4F - yOffset), (double)depth).tex((double)uMin, (double)vMin).endVertex();
	            bufferbuilder.pos((double)(width), (double)(1.4F - yOffset), (double)depth).tex((double)uMax, (double)vMin).endVertex();
	            tessellator.draw();

	            // Update height, yOffset, and width for the next fire layer
	            height -= 0.45F;
	            yOffset -= 0.45F;
	            width *= 0.9F;
	            depth += 0.03F;
	        }

	        // Restore the matrix and lighting
	        GlStateManager.popMatrix();
	        GlStateManager.enableLighting();
	    }
	}

}
