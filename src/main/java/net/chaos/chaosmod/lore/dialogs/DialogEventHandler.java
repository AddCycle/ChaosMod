package net.chaos.chaosmod.lore.dialogs;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.entity.EntityViking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class DialogEventHandler {

	@SubscribeEvent
	public void onRenderLivingSpecials(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
	    if (!(event.getEntity() instanceof EntityViking)) return;

	    EntityViking entity = (EntityViking) event.getEntity();
	    String dialog = entity.getDialogText();

	    if (dialog == null || dialog.isEmpty()) return;

	    renderDialogBox(event.getX(), event.getY(), event.getZ(), dialog, event.getEntity(), event.getPartialRenderTick());
	}
	
	public void renderDialogBox(double x, double y, double z, String text, Entity entity, float partialTicks) {
		Minecraft mc = Minecraft.getMinecraft();
	    RenderManager renderManager = mc.getRenderManager();

	    GlStateManager.pushMatrix();

	    // Translate to entity position
	    GlStateManager.translate(x, y + entity.height + 0.5, z);
	    // Rotate so it faces the player
	    GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	    GlStateManager.scale(-0.025F, -0.025F, 0.025F); // scale to readable size

	    // Disable depth and lighting so it renders over everything
	    GlStateManager.disableLighting();
	    GlStateManager.disableDepth();
	    GlStateManager.disableTexture2D();
	    GlStateManager.enableBlend();

	    int boxWidth = mc.fontRenderer.getStringWidth(text) + 10;
	    int boxHeight = 10;

	    // Draw white background box
	    Tessellator tess = Tessellator.getInstance();
	    BufferBuilder buffer = tess.getBuffer();
	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	    buffer.pos(-boxWidth / 2, -boxHeight / 2, 0.0).color(255, 255, 255, 200).endVertex();
	    buffer.pos(-boxWidth / 2, boxHeight / 2, 0.0).color(255, 255, 255, 200).endVertex();
	    buffer.pos(boxWidth / 2, boxHeight / 2, 0.0).color(255, 255, 255, 200).endVertex();
	    buffer.pos(boxWidth / 2, -boxHeight / 2, 0.0).color(255, 255, 255, 200).endVertex();
	    tess.draw();

	    GlStateManager.enableTexture2D();

	    // Draw text centered
	    mc.fontRenderer.drawString(text, -mc.fontRenderer.getStringWidth(text) / 2, -4, 0x000000);

	    // Restore state
	    GlStateManager.enableDepth();
	    GlStateManager.enableLighting();
	    GlStateManager.disableBlend();

	    GlStateManager.popMatrix();
	}

}
