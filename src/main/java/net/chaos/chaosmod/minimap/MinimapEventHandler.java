package net.chaos.chaosmod.minimap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(Side.CLIENT)
public class MinimapEventHandler {
	private static final int MAP_SIZE = 102;
    private static final int PIXEL_SIZE = 5;
    private static final Minecraft mc = Minecraft.getMinecraft();
	/*
	 * TODO : Note for me later : Don't use RenderGameOverlayEvent alone use one phase : Pre, Post, Chat, BossInfo => too laggy
	 */

	@SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
		Minecraft mc = Minecraft.getMinecraft();
		if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }

        // Defensive: avoid rendering if player not present or game not in focus
        if (mc.player == null || mc.isGamePaused()) {
            return;
        }
        drawMap(event.getResolution());
    }
	
	private static void drawMap(ScaledResolution resolution) {
        // Cache player position locally
        final BlockPos playerPos = mc.player.getPosition();

        // Get colors once (potentially heavy)
        int[][] cachedColors = Renderer.getMapBlockColors(playerPos, MAP_SIZE);

        // Precompute some constants to avoid repeated calculation in loop
        int pixelSize = PIXEL_SIZE;

        // Draw pixels in row-major order for better cache locality
        for (int x = 0; x < MAP_SIZE; x++) {
            int[] rowColors = cachedColors[x];
            for (int y = 0; y < MAP_SIZE; y++) {
            	int flippedY = MAP_SIZE - y - 1;
            	int flippedX = MAP_SIZE - x - 1;
                Renderer.drawPixel(resolution, pixelSize, flippedX, flippedY, cachedColors[x][y]);
                // Renderer.drawPixel(resolution, pixelSize, x, y, rowColors[y]);
            }
        }
        
        Renderer.drawTransparentMap(resolution, pixelSize);
        drawArrow(resolution, MAP_SIZE, pixelSize);
        
    }

	private static void drawArrow(ScaledResolution resolution, int mapSize, int pixelSize) {
		float playerYaw = mc.player.rotationYaw;
        /*int centerX = mapSize * pixelSize / 2;
        int centerY = mapSize * pixelSize / 2;*/
        int centerX = (int)(resolution.getScaledWidth() / 2);
        int centerY = (int)(resolution.getScaledWidth() / 2);
        float angle = mc.player.rotationYaw;
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        Renderer.drawPlayerArrow(resolution, centerX - 170, centerY - 170, playerYaw, 0xFFFFFF); // white arrow

        // Restore GL state
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
	}

}
