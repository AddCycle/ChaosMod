package net.chaos.chaosmod.minimap;

import java.util.List;

import net.chaos.chaosmod.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MinimapEventHandler {
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
	
	// v1 perfectly working if issue : this will work
	private static void drawMap(ScaledResolution resolution) {
		Minecraft mc = Minecraft.getMinecraft();
	    int mapSize = ModConfig.minimapSize;
	    int pixelSize = ModConfig.pixelSize;

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
		if (ModConfig.displayOverlay) Renderer.drawTransparentMap(resolution, pixelSize);
		if (ModConfig.displayArrow) drawArrow(resolution, mapSize, pixelSize);
		drawPlayersArrows(resolution);
        
    }
	
	// v2 tentative echouee je pense
	/*private static void drawMap(ScaledResolution resolution) {
	    Minecraft mc = Minecraft.getMinecraft();
	    World world = mc.world;
	    EntityPlayer player = mc.player;

	    int mapSize = ModConfig.minimapSize;
	    int pixelSize = ModConfig.pixelSize;

	    // Center the minimap on screen
	    int minimapCenterX = resolution.getScaledWidth() / 2 - (mapSize * pixelSize) / 2;
	    int minimapCenterY = resolution.getScaledHeight() / 2 - (mapSize * pixelSize) / 2;

	    int radiusChunks = mapSize / 16 / 2; // how many chunks radius to render around player

	    int playerChunkX = player.chunkCoordX;
	    int playerChunkZ = player.chunkCoordZ;

	    for (int dx = -radiusChunks; dx <= radiusChunks; dx++) {
	        for (int dz = -radiusChunks; dz <= radiusChunks; dz++) {
	            int chunkX = playerChunkX + dx;
	            int chunkZ = playerChunkZ + dz;

	            int[][] colors = ChunkColorCache.getChunkColors(world, chunkX, chunkZ);

	            for (int cx = 0; cx < 16; cx++) {
	                for (int cz = 0; cz < 16; cz++) {
	                    int color = colors[cx][cz];

	                    // flip vertically inside chunk by reversing cz on screen Y coordinate only
	                    int screenX = minimapCenterX + ((dx + radiusChunks) * 16 + cx) * pixelSize;
	                    int screenY = minimapCenterY + ((dz + radiusChunks) * 16 + (15 - cz)) * pixelSize;

	                    Renderer.drawPixel(resolution, pixelSize, screenX / pixelSize, screenY / pixelSize, color);
	                }
	            }
	        }
	    }

	    if (ModConfig.displayOverlay)
	        Renderer.drawTransparentMap(resolution, pixelSize);

	    if (ModConfig.displayArrow)
	        drawArrow(resolution, mapSize, pixelSize);
	}*/
	
	private static void drawPlayersArrows(ScaledResolution resolution) {
		Minecraft mc = Minecraft.getMinecraft();
		List<EntityPlayer> players = mc.world.playerEntities;
		EntityPlayerSP localPlayer = mc.player;
		for (EntityPlayer player : mc.world.playerEntities) {
		    if (player == localPlayer) continue; // Skip self
		    double distanceSq = player.getDistanceSq(localPlayer);
		    if (distanceSq > 256 * 256) continue; // Skip distant players (example: 256 block range)
		    drawArrowAt(resolution, player.getPosition(), player);
		    
		}
	}

	private static void drawArrow(ScaledResolution resolution, int mapSize, int pixelSize) {
		Minecraft mc = Minecraft.getMinecraft();
		float playerYaw = mc.player.rotationYaw;
		float scale = resolution.getScaleFactor();

	    // Calculate center in scaled coordinates (divide by scale)
	    int centerX = (int)((mapSize * pixelSize) / 2f / scale);
	    int centerY = (int)((mapSize * pixelSize) / 2f / scale);
        // int centerX = (int)(resolution.getScaledWidth() / 2);
        // int centerY = (int)(resolution.getScaledWidth() / 2);
        float angle = mc.player.rotationYaw;
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        Renderer.drawPlayerArrow(resolution, centerX, centerY, playerYaw, 0xFFFFFF); // white arrow

        // Restore GL state
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
	}

	private static void drawArrowAt(ScaledResolution resolution, BlockPos pos, EntityPlayer player) {
		Minecraft mc = Minecraft.getMinecraft();
		float playerYaw = player.rotationYaw;
		float scale = resolution.getScaleFactor();

	    // Calculate center in scaled coordinates (divide by scale)
	    int centerX = (int)((pos.getX()) / scale);
	    int centerY = (int)((pos.getY()) / scale);
        // int centerX = (int)(resolution.getScaledWidth() / 2);
        // int centerY = (int)(resolution.getScaledWidth() / 2);
        float angle = player.rotationYaw;
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        Renderer.drawPlayerArrow(resolution, centerX, centerY, playerYaw, 0xff0000); // red arrow

        // Restore GL state
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
	}
}
