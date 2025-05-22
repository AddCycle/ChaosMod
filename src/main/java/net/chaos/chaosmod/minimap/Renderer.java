package net.chaos.chaosmod.minimap;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import util.Reference;

public class Renderer {
	private static final ResourceLocation opaqueLogo = new ResourceLocation(Reference.MODID, "textures/gui/minimap/map256.png");
	private static final ResourceLocation transparentBackground = new ResourceLocation(Reference.MODID, "textures/gui/minimap/map256.png");
	private static final ResourceLocation transparentMap = new ResourceLocation(Reference.MODID, "textures/gui/minimap/frame.png");
    private static final int WIDTH = 256;
    private static final int HEIGHT = 256;
 
    public static void drawOpaqueSprite(ScaledResolution resolution) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(opaqueLogo);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        float w = WIDTH / ((float)resolution.getScaleFactor());
        float h = HEIGHT / ((float)resolution.getScaleFactor());
        buffer.pos(0,0,0).tex(0, 0).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(0,h,0).tex(0, 1f).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(w, h,0).tex(1f, 1f).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(w,0,0).tex(1f, 0).color(1f, 1f, 1f, 1f).endVertex();
        tessellator.draw();
    }
 
    public static void drawTransparentBackground(ScaledResolution resolution) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(transparentBackground);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        float offset = 40;
        float w = WIDTH / ((float)resolution.getScaleFactor()) + offset;
        float h = HEIGHT / ((float)resolution.getScaleFactor()) + offset;
        buffer.pos(0,0,0).tex(0, 0).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(0,h,0).tex(0, 1f).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(w, h,0).tex(1f, 1f).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(w,0,0).tex(1f, 0).color(1f, 1f, 1f, 1f).endVertex();
        tessellator.draw();
    }

    public static void drawTransparentMap(ScaledResolution resolution) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(transparentMap);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        float offset = 40;
        float w = WIDTH / ((float)resolution.getScaleFactor()) + offset;
        float h = HEIGHT / ((float)resolution.getScaleFactor()) + offset;
        buffer.pos(0,0,0).tex(0, 0).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(0,h,0).tex(0, 1f).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(w, h,0).tex(1f, 1f).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(w,0,0).tex(1f, 0).color(1f, 1f, 1f, 1f).endVertex();
        tessellator.draw();
    }
 
    public static void drawModel(ScaledResolution resolution) {
        Minecraft mc = Minecraft.getMinecraft();
        GuiInventory.drawEntityOnScreen(100,100,resolution.getScaleFactor()*10, 0f, 0f, mc.player);
    }

    public static void drawMinimap(int cx, int cy, int radius) {
    	Minecraft mc = Minecraft.getMinecraft();
        BlockPos playerPos = mc.player.getPosition();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        for (int dx = -8; dx <= 8; dx++) {
            for (int dz = -8; dz <= 8; dz++) {
                int worldX = playerPos.getX() + dx;
                int worldZ = playerPos.getZ() + dz;
                int worldY = mc.world.getTopSolidOrLiquidBlock(new BlockPos(worldX, 0, worldZ)).getY();
                BlockPos pos = new BlockPos(worldX, worldY - 1, worldZ);

                int color = mc.world.getBlockState(pos).getMaterial().getMaterialMapColor().colorValue;
                // System.out.println("color : " + color);
                Color c = new Color(color);

                int px = cx + dx;
                int py = cy + dz;

                buffer.pos(px, py, 0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();
                buffer.pos(px + 1, py, 0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();
                buffer.pos(px + 1, py + 1, 0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();
                buffer.pos(px, py + 1, 0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();
            }
        }

        tessellator.draw();
    }
    
    private static int getWorldTopColor(BlockPos pos) {
		Minecraft mc = Minecraft.getMinecraft();

        BlockPos pos_ = pos;
        int color = mc.world.getBlockState(pos_).getMaterial().getMaterialMapColor().colorValue;
    	return color;
    }
    
    public static int[][] getMapBlockColors(BlockPos playerPos, int mapSize) {
		Minecraft mc = Minecraft.getMinecraft();
		BlockPos[][] tilesPos = new BlockPos[mapSize][mapSize];
	    int[][] colors = new int[mapSize][mapSize];
        for (int dx = -mapSize / 2; dx < mapSize / 2; dx++) {
            for (int dz = -mapSize / 2; dz < mapSize / 2; dz++) { // for 10 => between -5 and 5
                int worldX = playerPos.getX() + dx;
                int worldZ = playerPos.getZ() + dz;
                // prev : int worldY = mc.world.getTopSolidOrLiquidBlock(new BlockPos(worldX, 0, worldZ)).getY();
                // actual
                int worldY = mc.world.getHeight(worldX, worldZ);
                // System.out.println("index : " + (dx + mapSize / 2) + " " + (dz + mapSize / 2));
                tilesPos[dx + (mapSize / 2)][dz + (mapSize / 2)] = new BlockPos(worldX, worldY - 1, worldZ);
            }
        }
    	for (int i = 0; i < mapSize; i++) {
    		for (int j = 0; j < mapSize; j++) {
    			colors[i][j] = getWorldTopColor(tilesPos[i][j]);
    		}
    	}
    	return colors;
    }
    
    // New TODO : optimizing
    public static void drawPixel(ScaledResolution resolution, int size, float posX, float posY, int color) {
        // Color c = (color != 9923917) ? new Color(color) : Color.cyan;
    	Color c = new Color(color);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        float pixel = size;
        float scale = resolution.getScaleFactor();
        float horizontal = pixel / scale;
        float vertical = pixel / scale;
        float x = horizontal * posX;
        float y = vertical * posY;

        buffer.pos(x, y, 0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();
        buffer.pos(x, y + vertical, 0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();
        buffer.pos(x + horizontal, y + vertical, 0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();
        buffer.pos(x + horizontal, y, 0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();

        tessellator.draw();
    }

    // Previous TODO : mine
	/*public static void drawPixel(ScaledResolution resolution, int size, float posX, float posY) {
		Minecraft mc = Minecraft.getMinecraft();
        BlockPos playerPos = mc.player.getPosition();
        int worldX = playerPos.getX();
        int worldZ = playerPos.getZ();
        int worldY = mc.world.getTopSolidOrLiquidBlock(new BlockPos(worldX, 0, worldZ)).getY();

        BlockPos pos = new BlockPos(worldX, worldY, worldZ);
        int color = getMapBlockColors(playerPos, 10)[(int) posX][(int) posY];
        Color c;
        if (color != 9923917)
        	c = new Color(color);
        else
        	c = new Color(Color.cyan.getRGB());
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        // float offset = 40;
        // float w = WIDTH / ((float)resolution.getScaleFactor()) + offset;
        // float h = HEIGHT / ((float)resolution.getScaleFactor()) + offset;
        // working
        // buffer.pos(0,0,0).color(1f, 1f, 1f, 1f).endVertex(); // resets the color to white
        // buffer.pos(0,h,0).color(1f, 1f, 1f, 1f).endVertex(); // draws until 
        // buffer.pos(w, h,0).color(1f, 1f, 1f, 1f).endVertex();
        // buffer.pos(w,0,0).color(1f, 1f, 1f, 1f).endVertex();

        // testing
        int pixel = size;
        float horizontal = (pixel / ((float)resolution.getScaleFactor())); // I want it to be the size of 1 pixel
        float vertical = (pixel / ((float)resolution.getScaleFactor()));
        float x = horizontal * posX;
        float y = vertical * posY;
        buffer.pos(x,y,0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex(); // resets the color to white
        buffer.pos(x,y + vertical,0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex(); // draws until 
        buffer.pos(x + horizontal, y + vertical,0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();
        buffer.pos(x + horizontal,y,0).color(c.getRed(), c.getGreen(), c.getBlue(), 255).endVertex();

        tessellator.draw();
    	System.out.println("colors : " + getMapBlockColors(playerPos, 10));
	}*/
}
