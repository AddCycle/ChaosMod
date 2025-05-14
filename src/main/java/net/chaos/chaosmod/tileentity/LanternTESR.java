package net.chaos.chaosmod.tileentity;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LanternTESR extends TileEntitySpecialRenderer<TileEntityLantern>{
	@Override
    public void render(TileEntityLantern te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GlStateManager.pushMatrix();
	    GlStateManager.translate(x + 0.5, y + 0.7, z + 0.5);

	    GlStateManager.disableLighting();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

	    GlStateManager.color(1.0f, 0.3f, 0.8f, 0.4f);

	    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("chaosmod:textures/misc/glow.png"));

	    // Rotate the quad to face the player (billboarding)
	    GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(Minecraft.getMinecraft().getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);

	    Tessellator tess = Tessellator.getInstance();
	    BufferBuilder buffer = tess.getBuffer();

	    double size = 0.3;

	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	    buffer.pos(-size, -size, 0).tex(0, 0).endVertex();
	    buffer.pos(-size, +size, 0).tex(0, 1).endVertex();
	    buffer.pos(+size, +size, 0).tex(1, 1).endVertex();
	    buffer.pos(+size, -size, 0).tex(1, 0).endVertex();
	    tess.draw();

	    GlStateManager.disableBlend();
	    GlStateManager.enableLighting();
	    GlStateManager.popMatrix();
    }

}
