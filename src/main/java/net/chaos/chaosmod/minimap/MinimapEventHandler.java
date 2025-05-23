package net.chaos.chaosmod.minimap;

import net.chaos.chaosmod.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MinimapEventHandler {
	private static final int MAP_SIZE = 102;
    private static final int PIXEL_SIZE = 5;
	/*
	 * TODO : Note for me later : Don't use RenderGameOverlayEvent alone use one phase : Pre, Post, Chat, BossInfo => too laggy
	 */

	@SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
		
		if (!ModConfig.isMinimapEnabled) return;


        // Defensive: avoid rendering if player not present or game not in focus
        if (mc.player == null || mc.isGamePaused()) {
            return;
        }
        drawMap(event.getResolution());
    }
	
	private static void drawMap(ScaledResolution resolution) {
		Minecraft mc = Minecraft.getMinecraft();
	    int mapSize = 100;
	    int pixelSize = 5;

	    Renderer.MapData mapData = Renderer.getMapBlockColorsAndHeights(mc.player.getPosition(), mapSize);
	    int[][] colors = mapData.colors;
	    int[][] heights = mapData.heights;

	    for (int i = 0; i < mapSize; i++) {
	        for (int j = 0; j < mapSize; j++) {
	            int height = heights[i][j];
	            int baseColor = colors[i][j];

	            // Compare with north-west neighbor
	            int neighborI = Math.max(0, i - 1);
	            int neighborJ = Math.max(0, j - 1);
	            int neighborHeight = heights[neighborI][neighborJ];

	            int diff = height - neighborHeight;
	            float brightness = 1.0f + (diff * 0.15f); // adjust multiplier to tune effect
	            brightness = Math.max(0.5f, Math.min(1.5f, brightness)); // clamp

	            int shadedColor = Renderer.applyBrightness(baseColor, brightness);

	            // Flip vertically for north-facing up
	            // int flippedJ = mapSize - j - 1;
	            Renderer.drawPixel(resolution, pixelSize, i, j, shadedColor);
	        }
	    }
		if (!ModConfig.displayOverlay) return;
        Renderer.drawTransparentMap(resolution, pixelSize);
		if (!ModConfig.displayArrow) return;
        drawArrow(resolution, MAP_SIZE, pixelSize);
        
    }

	private static void drawArrow(ScaledResolution resolution, int mapSize, int pixelSize) {
		Minecraft mc = Minecraft.getMinecraft();
		float playerYaw = mc.player.rotationYaw;
        /*int centerX = mapSize * pixelSize / 2;
        int centerY = mapSize * pixelSize / 2;*/
        int centerX = (int)(resolution.getScaledWidth() / 2);
        int centerY = (int)(resolution.getScaledWidth() / 2);
        float angle = mc.player.rotationYaw;
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        Renderer.drawPlayerArrow(resolution, centerX - 170, centerY - 170, playerYaw, 0xFFFFFF); // white arrow

        // Restore GL state
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
	}

}
