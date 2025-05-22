package net.chaos.chaosmod.minimap;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import util.Reference;

public class Renderer {
	private static final ResourceLocation opaqueLogo = new ResourceLocation(Reference.MODID, "textures/gui/minimap/map256.png");
	// private static final ResourceLocation transparentLogo = new ResourceLocation(Reference.MODID, "textures/gui/minimap/map128.png");
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
 
    public static void drawTransparentSprite(ScaledResolution resolution) {
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

    private static void drawMinimap(int cx, int cy, int radius) {
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
}
