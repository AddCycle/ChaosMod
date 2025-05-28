package net.chaos.chaosmod.minimap;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.chaos.chaosmod.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MinimapEventHandler {
	public static Map<UUID, Integer> map = new HashMap<>(); // stores the color of the player arrow to draw on the minimap
	private static int colorIndex = 0;
	public static List<Integer> colors = Arrays.asList(0xff0000, 0x0000ff, 0x00ff00, 0xff8400, 0x8d00a3); // red, blue, green, orange, purple
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
		drawPlayersArrows(resolution, mapSize, pixelSize);
        
    }
	
	private static void drawPlayersArrows(ScaledResolution resolution, int mapSize, int pixelSize) {
		Random rand = new Random();
		Minecraft mc = Minecraft.getMinecraft();
		List<EntityPlayer> players = mc.world.playerEntities;
		EntityPlayerSP localPlayer = mc.player;
		for (EntityPlayer player : players) {
		    if (player == localPlayer) continue; // Skip self
		    UUID playerId = player.getUniqueID();
		    if (!map.containsKey(playerId)) {
	            int assignedColor = 0x000000;
	            if (colorIndex < colors.size()) {
	                assignedColor = colors.get(colorIndex);
	            } else {
	            	Color color = new Color(rand.nextInt(0xffffff));
	            	if (!map.containsValue(color.getRGB())) assignedColor = color.getRGB(); // fallback color that is not in the map
	            }
	            map.put(playerId, assignedColor);
	            colorIndex++;
	        }
		    double distanceX = getDistanceX(player, localPlayer);
		    double distanceZ = getDistanceZ(player, localPlayer);
		    double pythagorean = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
		    if (pythagorean > mapSize / 3) continue; // Skip distant players (example: map block range)
		    drawArrowAt(resolution, mapSize, pixelSize, distanceX, distanceZ, player, map.get(playerId));
		}
	}
	
	private static double getDistanceX(EntityPlayer pl1, EntityPlayer pl2) {
		double d0 = (double)(pl1.posX - pl2.posX);
		return d0;
	}

	private static double getDistanceZ(EntityPlayer pl1, EntityPlayer pl2) {
		double d2 = (double)(pl1.posZ - pl2.posZ);
		return d2;
	}
	
	/*
	 * Pythagorean theorem to get two players distance using x,z coordinates (omitting height : y axis)
	 */
	/*private static double getPlayersDistanceY(EntityPlayer player1, EntityPlayer player2) {
		double d0 = (double)Math.abs(player1.posX - player2.posX);
		double d2 = (double)Math.abs(player1.posZ - player2.posZ);
        return Math.sqrt(d0 * d0 + d2 * d2);
	}*/

	private static void drawArrow(ScaledResolution resolution, int mapSize, int pixelSize) {
		Minecraft mc = Minecraft.getMinecraft();
		float playerYaw = mc.player.rotationYaw;
		float scale = resolution.getScaleFactor();

	    // Calculate center in scaled coordinates (divide by scale)
	    int centerX = (int)((mapSize * pixelSize) / 2f / scale);
	    int centerY = (int)((mapSize * pixelSize) / 2f / scale);
        // int centerX = (int)(resolution.getScaledWidth() / 2);
        // int centerY = (int)(resolution.getScaledWidth() / 2);
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        Renderer.drawPlayerArrow(resolution, centerX, centerY, playerYaw, 0xFFFFFF, mapSize); // white arrow for the local player

        // Restore GL state
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
	}

	private static void drawArrowAt(ScaledResolution resolution, int mapSize, int pixelSize, double distanceX, double distanceZ, EntityPlayer player, int color) {
		Minecraft mc = Minecraft.getMinecraft();
		float playerYaw = player.rotationYaw;
		float scale = resolution.getScaleFactor();
		// System.out.println("distance between players : " + distanceSq);

	    // Calculate center in scaled coordinates (divide by scale)
	    int centerX = (int)((mapSize * pixelSize) / 2f / scale);
	    int centerY = (int)((mapSize * pixelSize) / 2f / scale);
        // int centerX = (int)(resolution.getScaledWidth() / 2);
        // int centerY = (int)(resolution.getScaledWidth() / 2);
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        /*
         * FIXME : Use dot product to project on the horizontal axis to see the actual distance between the players (note that the distance is squared for now)
         */
        // double pythagorean = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);
        // (pythagorean > mapSize / 3)
        Renderer.drawPlayerArrow(resolution, Math.min(centerX + (int)(distanceX), mapSize), Math.min(centerY + (int) (distanceZ), mapSize), playerYaw, color, mapSize);

        // Restore GL state
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
	}
}
