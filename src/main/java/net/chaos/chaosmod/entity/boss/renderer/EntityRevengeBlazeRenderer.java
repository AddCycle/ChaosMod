package net.chaos.chaosmod.entity.boss.renderer;

import net.chaos.chaosmod.entity.boss.entities.EntityRevengeBlazeBoss;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EntityRevengeBlazeRenderer extends RenderLiving<EntityRevengeBlazeBoss> {
	public static final ResourceLocation LAYER1 = new ResourceLocation(Reference.MODID, "textures/entity/boss/revenge_blaze_1.png");
	public static final ResourceLocation LAYER2 = new ResourceLocation(Reference.MODID, "textures/entity/boss/revenge_blaze_2.png");
	public static final ResourceLocation FIRE_LAYER_0 = new ResourceLocation(Reference.MODID, "textures/entity/boss/fire_layer_0.png");
	public static final ResourceLocation FIRE_LAYER_1 = new ResourceLocation(Reference.MODID, "textures/entity/boss/fire_layer_1.png");
	public static final ResourceLocation BLUE_FIRE_LAYER_0 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_0.png");
	public static final ResourceLocation BLUE_FIRE_LAYER_1 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_1.png");
	
	/* Only if the model changes
	 * private final ModelBase normalModel = new ModelBossNormal();
	 * private final ModelBase transformedModel = new ModelBossTransformed();
	 */
	
	public EntityRevengeBlazeRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelBlaze(), 0.5f);
		this.addLayer(new LayerDeathBoss<EntityRevengeBlazeBoss>(this, 0, 0, 200));
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
    protected float handleRotationFloat(EntityRevengeBlazeBoss livingBase, float partialTicks) {
    	return super.handleRotationFloat(livingBase, partialTicks);
    }
    
    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {
    	// super.doRenderShadowAndFire(entityIn, x, y, z, yaw, partialTicks);
    	if (entityIn.isBurning()) renderEntityOnFireCustom(entityIn, x, y, z, partialTicks);
    }
    
    private void renderEntityOnFireCustom(Entity entity, double x, double y, double z, float partialTicks)
    {
    	if (entity instanceof EntityRevengeBlazeBoss) {
    		EntityRevengeBlazeBoss revengeBlaze = (EntityRevengeBlazeBoss) entity;
    		if (revengeBlaze.isTransformed()) {
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
    		} else {
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
    				ResourceLocation currentTexture = ((ageInTicks / 2) % 2 == 0) ? FIRE_LAYER_1 : FIRE_LAYER_0;
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
    }
    
    // This should kill the death time rotation
    @Override
    protected void applyRotations(EntityRevengeBlazeBoss entityLiving, float p_77043_2_, float rotationYaw,
    		float partialTicks) {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
    	// super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }
}