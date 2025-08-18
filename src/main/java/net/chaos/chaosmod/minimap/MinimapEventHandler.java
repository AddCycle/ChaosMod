package net.chaos.chaosmod.minimap;

import static net.chaos.chaosmod.config.ModConfig.CLIENT;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MinimapEventHandler {
	public static Map<UUID, Integer> map = new HashMap<>(); // stores the color of the player arrow to draw on the minimap
	private static int colorIndex = 0;
	public static List<Integer> colors = Arrays.asList(0xff0000, 0x0000ff, 0x00ff00, 0xff8400, 0x8d00a3); // red, blue, green, orange, purple
	private static final int UPDATE_INTERVAL = 5; // every 5 ticks
	private static int updateTick = 0;
	private static Renderer.MapData cachedMapData = null;
	private int lastPlayerX;
	private int lastPlayerZ;
	/*
	 * TODO : Note for me later : Don't use RenderGameOverlayEvent alone use one phase : Pre, Post, Chat, BossInfo => too laggy
	 */

	@SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
		if (!CLIENT.isMinimapEnabled) return;
		if (event.getType() != ElementType.ALL) return;

		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;

        // Defensive: avoid rendering if player not present or game not in focus
        if (player == null || mc.isGamePaused()) {
            return;
        }
        if (Math.abs(player.posX - lastPlayerX) >= 1 || Math.abs(player.posZ - lastPlayerZ) >= 1) {
            Renderer.updateMinimapTexture(player);
            lastPlayerX = (int)player.posX;
            lastPlayerZ = (int)player.posZ;
        }
        Renderer.drawMinimap(event.getResolution(), CLIENT.pixelSize);
		if (CLIENT.displayOverlay) Renderer.drawTransparentMap(event.getResolution(), CLIENT.pixelSize);
		if (CLIENT.displayArrow) drawArrow(event.getResolution(), CLIENT.minimapSize, CLIENT.pixelSize);
		// FIXME : later re-enable player arrows because Louis want to troll other players
		// Make an item in order to make this feature enabled like while holding an item in their hands
		// drawPlayersArrows(resolution, mapSize, pixelSize);
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

	private static void drawArrow(ScaledResolution resolution, int mapSize, int pixelSize) {
		Minecraft mc = Minecraft.getMinecraft();
		float playerYaw = mc.player.rotationYaw;
		float scale = resolution.getScaleFactor();
		int side = mapSize * pixelSize;

	    // center of the screen
//		int centerX = (resolution.getScaledWidth() - mapSize * pixelSize) / 2 + (mapSize * pixelSize) / 2;
//		int centerY = (resolution.getScaledHeight() - mapSize * pixelSize) / 2 + (mapSize * pixelSize) / 2;

		int centerX = side / 2;
		int centerY = side / 2;

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
		float playerYaw = player.rotationYaw;

	    // Calculate center in scaled coordinates (divide by scale)
	    int centerX = (mapSize * pixelSize) / 2;
	    int centerY = (mapSize * pixelSize) / 2;

        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();

        Renderer.drawPlayerArrow(resolution, Math.min(centerX + (int)(distanceX), mapSize), Math.min(centerY + (int) (distanceZ), mapSize), playerYaw, color, mapSize);

        // Restore GL state
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
	}
}
