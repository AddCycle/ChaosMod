package net.chaos.chaosmod.entity.projectile.render;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.entity.projectile.EntityRock;
import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EntityRockRenderer extends Render<EntityRock> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/projectile/rock.png");

	public EntityRockRenderer(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRock entity) {
		return TEXTURE;
	}
	
	@Override
	public void doRender(EntityRock entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
	    this.bindEntityTexture(entity); // Your custom blue fireball texture
	    GlStateManager.translate((float)x, (float)y, (float)z);
	    GlStateManager.enableRescaleNormal();
	    GlStateManager.scale(0.5F, 0.5F, 0.5F); // Half size so it's not too huge

	    // Billboard facing player
	    GlStateManager.rotate(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * -this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

	    if (this.renderOutlines)
	    {
	        GlStateManager.enableColorMaterial();
	        GlStateManager.enableOutlineMode(this.getTeamColor(entity));
	    }

	    Tessellator tessellator = Tessellator.getInstance();
	    BufferBuilder bufferbuilder = tessellator.getBuffer();

	    // Instead of getting particle icon, just use full texture (0.0 - 1.0 UVs)
	    float minU = 0.0F;
	    float maxU = 1.0F;
	    float minV = 0.0F;
	    float maxV = 1.0F;

	    bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_NORMAL);
	    bufferbuilder.pos(-0.5D, -0.5D, 0.0D).tex(minU, maxV).normal(0.0F, 1.0F, 0.0F).endVertex();
	    bufferbuilder.pos(0.5D, -0.5D, 0.0D).tex(maxU, maxV).normal(0.0F, 1.0F, 0.0F).endVertex();
	    bufferbuilder.pos(0.5D, 0.5D, 0.0D).tex(maxU, minV).normal(0.0F, 1.0F, 0.0F).endVertex();
	    bufferbuilder.pos(-0.5D, 0.5D, 0.0D).tex(minU, minV).normal(0.0F, 1.0F, 0.0F).endVertex();
	    tessellator.draw();

	    if (this.renderOutlines)
	    {
	        GlStateManager.disableOutlineMode();
	        GlStateManager.disableColorMaterial();
	    }

	    GlStateManager.disableRescaleNormal();
	    GlStateManager.popMatrix();
	    
	    super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}
