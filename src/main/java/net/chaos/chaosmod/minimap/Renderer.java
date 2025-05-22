package net.chaos.chaosmod.minimap;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.config.ModConfig;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
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
    	GlStateManager.pushMatrix();
    	GlStateManager.disableTexture2D();
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
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static void drawTransparentMap(ScaledResolution resolution, int pixelSize) {
    	GlStateManager.pushMatrix();
    	int sizeOverlayConfig = ModConfig.minimapSize;
        Minecraft.getMinecraft().getTextureManager().bindTexture(transparentMap);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        float w = (WIDTH * (pixelSize - 3)) / ((float)resolution.getScaleFactor()) - 1 + sizeOverlayConfig;
        float h = (HEIGHT * (pixelSize - 3)) / ((float)resolution.getScaleFactor()) - 1 + sizeOverlayConfig;
        buffer.pos(0,0,0).tex(0, 0).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(0,h,0).tex(0, 1f).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(w, h,0).tex(1f, 1f).color(1f, 1f, 1f, 1f).endVertex();
        buffer.pos(w,0,0).tex(1f, 0).color(1f, 1f, 1f, 1f).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
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
    
    private static int average(int a, int b) {
        return (a + b) / 2;
    }

    private static int blendBiomeColors(World world, BlockPos pos) {
        Biome biome = world.getBiome(pos);
        int grass = biome.getGrassColorAtPos(pos);
        int foliage = ColorizerFoliage.getFoliageColor(biome.getTemperature(pos), biome.getRainfall());

        int r = average((grass >> 16) & 0xFF, (foliage >> 16) & 0xFF);
        int g = average((grass >> 8) & 0xFF, (foliage >> 8) & 0xFF);
        int b = average(grass & 0xFF, foliage & 0xFF);

        return (r << 16) | (g << 8) | b;
    }
    
    private static int getWorldTopColor(World world, BlockPos pos) {
    	// v4
    	IBlockState state = world.getBlockState(pos);
        Material material = state.getMaterial();

        if (material == Material.GRASS || material == Material.LEAVES || material == Material.PLANTS) {
            return world.getBiome(pos).getGrassColorAtPos(pos);
        }

        // Foliage blending if it's a leaf block
        if (state.getBlock() instanceof BlockLeaves) {
            return ColorizerFoliage.getFoliageColor(world.getBiome(pos).getTemperature(pos), world.getBiome(pos).getRainfall());
        }

        // Try default map color fallback (block-based)
        try {
            return state.getMapColor(world, pos).colorValue;
        } catch (Exception ignored) {}

        // Safe fallback color
        return 0x555555;
    	// v3
    	// return blendBiomeColors(world, pos);
    	// v2
    	/*IBlockState state = world.getBlockState(pos);
    	Biome biome = world.getBiome(pos);
    	return biome.getGrassColorAtPos(pos);*/
    	// v1
        /*IBlockState state = world.getBlockState(pos);
        MapColor mapColor = state.getMaterial().getMaterialMapColor();
        return (mapColor != null) ? mapColor.colorValue : 0; // Default to black if null*/
    }
    
    public static int[][] getMapBlockColors(BlockPos playerPos, int mapSize) {
    	Minecraft mc = Minecraft.getMinecraft();
        World world = mc.world;

        int[][] colors = new int[mapSize][mapSize];
        int half = mapSize / 2;

        for (int dx = -half; dx < half; dx++) {
            for (int dz = -half; dz < half; dz++) {
                int x = playerPos.getX() + dx;
                int z = playerPos.getZ() + dz;
                int y = world.getHeight(x, z) - 1; // Safer: subtract 1 for surface block
                BlockPos topPos = new BlockPos(x, y, z);

                colors[dx + half][dz + half] = getWorldTopColor(world, topPos);
            }
        }

        return colors;
    }
    
    // New TODO : optimizing
    public static void drawPixel(ScaledResolution resolution, int size, float posX, float posY, int color) {
        // Color c = (color != 9923917) ? new Color(color) : Color.cyan;
    	// Extract color channels once
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D(); // Only if you're drawing raw colors (no textures)
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);

        float scale = resolution.getScaleFactor();
        float pixel = size / scale;
        float x = pixel * posX;
        float y = pixel * posY;

        buffer.pos(x, y, 0).color(r, g, b, 255).endVertex();
        buffer.pos(x, y + pixel, 0).color(r, g, b, 255).endVertex();
        buffer.pos(x + pixel, y + pixel, 0).color(r, g, b, 255).endVertex();
        buffer.pos(x + pixel, y, 0).color(r, g, b, 255).endVertex();

        tessellator.draw();

        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
    
    public static class MapData {
        public final int[][] colors;
        public final int[][] heights;

        public MapData(int[][] colors, int[][] heights) {
            this.colors = colors;
            this.heights = heights;
        }
    }

    public static MapData getMapBlockColorsAndHeights(BlockPos playerPos, int mapSize) {
        Minecraft mc = Minecraft.getMinecraft();
        int[][] heights = new int[mapSize][mapSize];
        int[][] colors = new int[mapSize][mapSize];

        for (int dx = -mapSize / 2; dx < mapSize / 2; dx++) {
            for (int dz = -mapSize / 2; dz < mapSize / 2; dz++) {
                int x = dx + mapSize / 2;
                int z = dz + mapSize / 2;
                int worldX = playerPos.getX() + dx;
                int worldZ = playerPos.getZ() + dz;
                int worldY = mc.world.getHeight(worldX, worldZ);
                BlockPos pos = new BlockPos(worldX, worldY - 1, worldZ);

                heights[x][z] = worldY;
                colors[x][z] = mc.world.getBlockState(pos).getMaterial().getMaterialMapColor().colorValue;
            }
        }
        return new MapData(colors, heights);
    }
    
    public static int applyBrightness(int color, float brightness) {
        Color c = new Color(color);
        int r = Math.min(255, Math.max(0, (int)(c.getRed() * brightness)));
        int g = Math.min(255, Math.max(0, (int)(c.getGreen() * brightness)));
        int b = Math.min(255, Math.max(0, (int)(c.getBlue() * brightness)));
        return new Color(r, g, b).getRGB();
    }
    
    public static void drawPlayerArrow(ScaledResolution resolution, int centerX, int centerY, float angleDegrees, int color) {
    	GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D(); // Prevent texture bleed
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha(); // Disable alpha to prevent fading
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);

        GlStateManager.translate(centerX, centerY, 0);
        GlStateManager.rotate(angleDegrees, 0, 0, 1);

        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);

        float size = 3f;
        buffer.pos(0, size * 1.7f, 0).color(r, g, b, 255).endVertex();   // Tip
        buffer.pos(size, -size, 0).color(r, g, b, 255).endVertex(); // Left
        buffer.pos(-size, -size, 0).color(r, g, b, 255).endVertex();  // Right

        tessellator.draw();

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.enableAlpha();
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
}
