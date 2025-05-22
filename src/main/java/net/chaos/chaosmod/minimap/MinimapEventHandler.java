package net.chaos.chaosmod.minimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

// FIXME : test this on server might crash due to access to Minecraft directly
@EventBusSubscriber(Side.CLIENT)
public class MinimapEventHandler {
	private static final Minecraft mc = Minecraft.getMinecraft();
	/*
	 * TODO : Note for me later : Don't use RenderGameOverlayEvent alone use one phase : Pre, Post, Chat, BossInfo => too laggy
	 */

	@SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) { // change this to post
        /*if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR || mc.world == null || mc.player == null)
            return;

        ScaledResolution res = new ScaledResolution(mc);
        int centerX = res.getScaledWidth() - 60;
        int centerY = 60;
        int radius = 40;

        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        drawMinimap(centerX, centerY, radius);

        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();*/
		if(event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            draw(event.getResolution());
        }
    }
	
	private static void draw(ScaledResolution resolution) {
        Renderer.drawTransparentSprite(resolution);
        // Renderer.drawModel(resolution);
    }

    
}
