package net.chaos.chaosmod.entity.boss.renderer;

import net.chaos.chaosmod.entity.boss.entities.EntityRevengeBlazeBoss;
import net.chaos.chaosmod.entity.boss.model.RevengeBlazeModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EntityRevengeBlazeRenderer extends RenderLiving<EntityRevengeBlazeBoss> {
	public static final ResourceLocation LAYER1 = new ResourceLocation(Reference.MODID, "textures/entity/boss/revenge_blaze_1.png");
	public static final ResourceLocation LAYER2 = new ResourceLocation(Reference.MODID, "textures/entity/boss/revenge_blaze_2.png");
	public static final ResourceLocation FIRE_LAYER_0 = new ResourceLocation(Reference.MODID, "textures/entity/boss/fire_layer_0.png");
	public static final ResourceLocation BLUE_FIRE_LAYER_0 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_0.png");
	public static final ResourceLocation BLUE_FIRE_LAYER_1 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_1.png");
	
	/* Only if the model changes
	 * private final ModelBase normalModel = new ModelBossNormal();
	 * private final ModelBase transformedModel = new ModelBossTransformed();
	 */

	public EntityRevengeBlazeRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelBlaze(), 0.5f);
		// this.addLayer(new LayerBlueFire<EntityRevengeBlazeBoss>(this));
	}

	@Override
    public ResourceLocation getEntityTexture(EntityRevengeBlazeBoss entity) {
        return entity.isTransformed() ? LAYER2 : LAYER1;
    }

   // Only if the model changes
    @Override
    protected void preRenderCallback(EntityRevengeBlazeBoss entitylivingbaseIn, float partialTickTime) {
        // if (entitylivingbaseIn.isTransformed()) {
    	this.mainModel = new ModelBlaze();
        /*} else {
            this.mainModel = normalModel;
        }*/
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }
    
    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
    	// super.doRenderShadowAndFire(entityIn, x, y, z, yaw, partialTicks);
    	renderEntityOnFireCustom(entityIn, x, y, z, partialTicks);
    }
    
    private void renderEntityOnFire(Entity entity, double x, double y, double z, float partialTicks) {
        GlStateManager.disableLighting();
        TextureMap texturemap = Minecraft.getMinecraft().getTextureMapBlocks();
        TextureAtlasSprite textureatlassprite = texturemap.getAtlasSprite("chaosmod:entity/boss/blue_fire_layer_0");
        TextureAtlasSprite textureatlassprite1 = texturemap.getAtlasSprite("chaosmod:entity/boss/blue_fire_layer_1");
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        float f = entity.width * 1.4F;
        GlStateManager.scale(f, f, f);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        float f1 = 0.5F;
        float f2 = 0.0F;
        float f3 = entity.height / f;
        float f4 = (float)(entity.posY - entity.getEntityBoundingBox().minY);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, -0.3F + (float)((int)f3) * 0.02F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        float f5 = 0.0F;
        int i = 0;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

        while (f3 > 0.0F)
        {
            TextureAtlasSprite textureatlassprite2 = i % 2 == 0 ? textureatlassprite : textureatlassprite1;
            this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            float f6 = textureatlassprite2.getMinU();
            float f7 = textureatlassprite2.getMinV();
            float f8 = textureatlassprite2.getMaxU();
            float f9 = textureatlassprite2.getMaxV();

            if (i / 2 % 2 == 0)
            {
                float f10 = f8;
                f8 = f6;
                f6 = f10;
            }

            bufferbuilder.pos((double)(f1 - 0.0F), (double)(0.0F - f4), (double)f5).tex((double)f8, (double)f9).endVertex();
            bufferbuilder.pos((double)(-f1 - 0.0F), (double)(0.0F - f4), (double)f5).tex((double)f6, (double)f9).endVertex();
            bufferbuilder.pos((double)(-f1 - 0.0F), (double)(1.4F - f4), (double)f5).tex((double)f6, (double)f7).endVertex();
            bufferbuilder.pos((double)(f1 - 0.0F), (double)(1.4F - f4), (double)f5).tex((double)f8, (double)f7).endVertex();
            f3 -= 0.45F;
            f4 -= 0.45F;
            f1 *= 0.9F;
            f5 += 0.03F;
            ++i;
        }

        tessellator.draw();
        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }
    
    private void renderEntityOnFireCustom(Entity entity, double x, double y, double z, float partialTicks)
    {
    	GlStateManager.disableLighting();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x, (float) y, (float) z);

        float scale = entity.width * 1.4F;
        GlStateManager.scale(scale, scale, scale);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        float width = 0.5F;
        float depth = 0.0F;
        float height = entity.height / scale;
        float yOffset = (float) (entity.posY - entity.getEntityBoundingBox().minY);

        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, -0.3F + (float)((int)height) * 0.02F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        final int totalFrames = 32; // your texture is 512px high: 512/16 = 32 frames
        final float frameHeight = 1.0F / totalFrames;
        float animationSpeed = 2.0F; // speed of animation

        int ageInTicks = entity.ticksExisted;
        float frameFloat = (ageInTicks + partialTicks) * animationSpeed;
        int currentFrame = (int) frameFloat % totalFrames;

        while (height > 0.0F) {
            // Alternate between two layers for flickering
            ResourceLocation currentTexture = ((ageInTicks / 2) % 2 == 0) ? BLUE_FIRE_LAYER_0 : BLUE_FIRE_LAYER_1;
            Minecraft.getMinecraft().getTextureManager().bindTexture(currentTexture);

            float uMin = 0.0F;
            float uMax = 1.0F;
            float vMin = frameHeight * currentFrame;
            float vMax = vMin + frameHeight;

            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos((double)(width), (double)(0.0F - yOffset), (double)depth).tex((double)uMax, (double)vMax).endVertex();
            bufferbuilder.pos((double)(-width), (double)(0.0F - yOffset), (double)depth).tex((double)uMin, (double)vMax).endVertex();
            bufferbuilder.pos((double)(-width), (double)(1.4F - yOffset), (double)depth).tex((double)uMin, (double)vMin).endVertex();
            bufferbuilder.pos((double)(width), (double)(1.4F - yOffset), (double)depth).tex((double)uMax, (double)vMin).endVertex();
            tessellator.draw();

            height -= 0.45F;
            yOffset -= 0.45F;
            width *= 0.9F;
            depth += 0.03F;
        }

        GlStateManager.popMatrix();
        GlStateManager.enableLighting();
    }
}