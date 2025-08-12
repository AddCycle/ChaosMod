package net.chaos.chaosmod.minimap;

import java.awt.Color;
import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.config.ModConfig;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.World;
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
    private static DynamicTexture minimapTexture;
    private static ResourceLocation minimapTextureLocation;
    private static int lastMapSize = -1;
    private static int lastPixelSize = -1;

    public static void init() {
        minimapTexture = new DynamicTexture(ModConfig.minimapSize, ModConfig.minimapSize);
        minimapTextureLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("minimap", minimapTexture);
    }
    
    public static void updateMinimapTexture(EntityPlayer player) {
        int mapSize = ModConfig.minimapSize;
        
        if (minimapTexture == null || mapSize != lastMapSize) {
            recreateMinimapTexture(mapSize);
            lastMapSize = mapSize;
        }

        Renderer.MapData mapData = getMapBlockColorsAndHeights(player.getPosition(), mapSize);

        int[][] colors = mapData.colors;
        int[][] heights = mapData.heights;

        int[] pixels = minimapTexture.getTextureData();
        if (pixels.length != mapSize * mapSize) {
            // Safety: avoid crash if size mismatch
            recreateMinimapTexture(mapSize);
            pixels = minimapTexture.getTextureData();
        }

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                int height = heights[i][j];
                int baseColor = colors[i][j];

                int neighborI = Math.max(0, i - 1);
                int neighborJ = Math.max(0, j - 1);
                int neighborHeight = heights[neighborI][neighborJ];

                int diff = height - neighborHeight;
                float brightness = 1.0f + (diff * 0.15f);
                brightness = Math.max(0.5f, Math.min(1.5f, brightness));

                int shadedColor = applyBrightness(baseColor, brightness);

                pixels[i + j * mapSize] = 0xFF000000 | shadedColor; // Ensure alpha = 255
            }
        }

        minimapTexture.updateDynamicTexture(); // Upload to GPU
    }
    
    public static void recreateMinimapTexture(int newSize) {
        if (minimapTexture != null) {
            minimapTexture.deleteGlTexture();
        }

        minimapTexture = new DynamicTexture(newSize, newSize);
        minimapTextureLocation = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("minimap", minimapTexture);

        lastMapSize = newSize;
        lastPixelSize = ModConfig.pixelSize; // assuming you have this
    }
    
    public static void clearMinimap() {
        if (minimapTexture == null) return;
        int[] pixels = minimapTexture.getTextureData();
        Arrays.fill(pixels, 0xFF000000); // Fill with opaque black
        minimapTexture.updateDynamicTexture();
    }
    
    public static void drawMinimap(ScaledResolution resolution, int pixelSize) {
        int mapSize = ModConfig.minimapSize;

        if (pixelSize != lastPixelSize || mapSize != lastMapSize) {
            recreateMinimapTexture(mapSize);  // Optional: if pixelSize affects size
            lastPixelSize = pixelSize;
            lastMapSize = mapSize;
        }
        
        if (minimapTexture == null || minimapTextureLocation == null) return;

        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.enableBlend();
        mc.getTextureManager().bindTexture(minimapTextureLocation);

        Tessellator tess = Tessellator.getInstance();
        BufferBuilder buffer = tess.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        float x = 0; // offsetX
        float y = 0; // offsetY
        float width = mapSize * pixelSize;
        float height = mapSize * pixelSize;

        buffer.pos(x, y + height, 0).tex(0, 1).endVertex();
        buffer.pos(x + width, y + height, 0).tex(1, 1).endVertex();
        buffer.pos(x + width, y, 0).tex(1, 0).endVertex();
        buffer.pos(x, y, 0).tex(0, 0).endVertex();

        tess.draw();
        GlStateManager.disableBlend();
    }
    
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
        float w = sizeOverlayConfig * pixelSize;
        float h = sizeOverlayConfig * pixelSize;
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
                
                int worldY = mc.world.getHeight(); // Start from max height
                BlockPos topPos = null;

                for (int y = worldY - 1; y >= 0; y--) {
                    BlockPos checkPos = new BlockPos(worldX, y, worldZ);
                    IBlockState state = mc.world.getBlockState(checkPos);
                    Material mat = state.getMaterial();

                    if (state.getBlock().isAir(state, mc.world, checkPos) && mat != Material.PLANTS && mat != Material.VINE && mat != Material.LEAVES) {
                        topPos = checkPos;
                        break;
                    }
                }

                if (topPos == null) {
                    continue; // Skip this column, nothing found
                }

                int color = mc.world.getBlockState(topPos).getMaterial().getMaterialMapColor().colorValue;
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
    }
    
    public static int getBlockColor(World world, int x, int z) {
        int y = world.getHeight(x, z); // get top block y at (x, z)
        return getMapColorAt(world, x, y - 1, z); // get color from top block
    }
    
    // Simpler :
    public static int getMapColorAt(World world, int x, int y, int z) {
        if (y < 0 || y > 255) return 0x000000; // transparent fallback
        IBlockState state = world.getBlockState(new BlockPos(x, y, z));
        MapColor mapColor = state.getMapColor(world, new BlockPos(x, y, z));
        if (mapColor == null) return 0x000000;
        return mapColor.colorValue;
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
        World world = mc.world;

        int[][] heights = new int[mapSize][mapSize];
        int[][] colors = new int[mapSize][mapSize];

        for (int dx = -mapSize / 2; dx < mapSize / 2; dx++) {
            for (int dz = -mapSize / 2; dz < mapSize / 2; dz++) {
                int x = dx + mapSize / 2;
                int z = dz + mapSize / 2;

                int worldX = playerPos.getX() + dx;
                int worldZ = playerPos.getZ() + dz;

                // Check if chunk is loaded before accessing
                BlockPos heightQueryPos = new BlockPos(worldX, 0, worldZ);
                if (!world.isBlockLoaded(heightQueryPos)) {
                    heights[x][z] = 0;
                    colors[x][z] = 0; // Or a default color like 0x000000
                    continue;
                }

                MapColor color = MapColor.AIR;
                int worldY = 0;

                for (int y = world.getActualHeight() - 1; y > 0; y--) {
                    BlockPos checkPos = new BlockPos(worldX, y, worldZ);
                    IBlockState state = world.getBlockState(checkPos);
                    Material mat = state.getMaterial();

                    if (!state.getBlock().isAir(state, world, checkPos) &&
                        mat != Material.PLANTS &&
                        mat != Material.VINE) {
                        color = mat.getMaterialMapColor();
                        worldY = y;
                        break;
                    }
                }

                heights[x][z] = worldY;
                colors[x][z] = color != null ? color.colorValue : 0;
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
    
    public static void drawPlayerArrow(ScaledResolution resolution, int centerX, int centerY, float angleDegrees, int color, int mapSize) {
    	GlStateManager.pushMatrix();
    	Minecraft mc = Minecraft.getMinecraft();
    	EntityPlayerSP localPlayer = mc.player;
    	int posX = MathHelper.floor(localPlayer.posX);
    	int posY = MathHelper.floor(localPlayer.posY);
    	int posZ = MathHelper.floor(localPlayer.posZ);

    	String coords = String.format("XYZ: %d / %d / %d", posX, posY, posZ);

    	int textX = 2;
    	int textY = centerY + mapSize / 4;

    	if (ModConfig.displayCoords) mc.fontRenderer.drawStringWithShadow(coords, textX, textY, 0xFFFFFF);
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
