package net.chaos.chaosmod.client.renderer.tileentity;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.tileentity.TileEntityBeam;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class TileEntityBeamRenderer extends TileEntitySpecialRenderer<TileEntityBeam> {
	
	@Override
	protected void drawNameplate(TileEntityBeam te, String str, double x, double y, double z, int maxDistance) {
		super.drawNameplate(te, str, x, y, z, maxDistance);
	}

	@Override
    public void render(TileEntityBeam te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y, z + 0.5);
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        // GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false); // Allow transparency

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();

        // float height = te.beamHeight;
        float height = 40f;
        float w = 0.2f; // Half-width of the beam (0.4x0.4 beam size)

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        // Front face (south)
        buffer.pos(-w, 0, -w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(w, 0, -w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(w, height, -w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(-w, height, -w).color(0f, 0.5f, 1f, 0.5f).endVertex();

        // Back face (north)
        buffer.pos(w, 0, w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(-w, 0, w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(-w, height, w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(w, height, w).color(0f, 0.5f, 1f, 0.5f).endVertex();

        // Left face (west)
        buffer.pos(-w, 0, w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(-w, 0, -w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(-w, height, -w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(-w, height, w).color(0f, 0.5f, 1f, 0.5f).endVertex();

        // Right face (east)
        buffer.pos(w, 0, -w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(w, 0, w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(w, height, w).color(0f, 0.5f, 1f, 0.5f).endVertex();
        buffer.pos(w, height, -w).color(0f, 0.5f, 1f, 0.5f).endVertex();

        // Optional: Top face (flat glow at top)
        buffer.pos(-w, height, -w).color(0f, 0.7f, 1f, 0.3f).endVertex();
        buffer.pos(w, height, -w).color(0f, 0.7f, 1f, 0.3f).endVertex();
        buffer.pos(w, height, w).color(0f, 0.7f, 1f, 0.3f).endVertex();
        buffer.pos(-w, height, w).color(0f, 0.7f, 1f, 0.3f).endVertex();

        tess.draw();

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }
    
    @Override
    public boolean isGlobalRenderer(TileEntityBeam te) {
    	return true;
    }
}