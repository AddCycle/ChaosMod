package net.chaos.chaosmod.world.events.overlay;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
// TODO : a frozen overlay
public class OverlayEffectsEvents {
	private static final String BLUE_FIRE_TEXTURE = "gui/blue_fire_layer_0";

	@SubscribeEvent
	public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
		if (true) return;
		if (event.getType() != ElementType.ALL) return;

		ScaledResolution res = event.getResolution();

//	    renderFireOverlay2D(res);
		renderBlueFire(res);
	}

	private static void renderFireOverlay2D(ScaledResolution res) {
	    Minecraft mc = Minecraft.getMinecraft();

	    mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	    TextureAtlasSprite sprite = mc.getTextureMapBlocks()
	            .getAtlasSprite("minecraft:blocks/fire_layer_0");

	    float u1 = sprite.getMinU();
	    float u2 = sprite.getMaxU();
	    float v1 = sprite.getMinV();
	    float v2 = sprite.getMaxV();

	    int w = res.getScaledWidth();
	    int h = res.getScaledHeight();

	    Tessellator t = Tessellator.getInstance();
	    BufferBuilder b = t.getBuffer();

	    GlStateManager.enableBlend();
	    GlStateManager.disableDepth();

	    b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	    b.pos(0, h, 0).tex(u1, v2).endVertex();
	    b.pos(w, h, 0).tex(u2, v2).endVertex();
	    b.pos(w, 0, 0).tex(u2, v1).endVertex();
	    b.pos(0, 0, 0).tex(u1, v1).endVertex();
	    t.draw();

	    GlStateManager.enableDepth();
	    GlStateManager.disableBlend();
	}
	
	private static void renderBlueFire(ScaledResolution res) {
	    Minecraft mc = Minecraft.getMinecraft();

	    mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
	    TextureAtlasSprite sprite = mc.getTextureMapBlocks()
	            .getAtlasSprite(Reference.PREFIX + BLUE_FIRE_TEXTURE);

	    float u1 = sprite.getMinU();
	    float u2 = sprite.getMaxU();
	    float v1 = sprite.getMinV();
	    float v2 = sprite.getMaxV();

	    int w = res.getScaledWidth();
	    int h = res.getScaledHeight();

	    Tessellator t = Tessellator.getInstance();
	    BufferBuilder b = t.getBuffer();

	    GlStateManager.enableBlend();
	    GlStateManager.disableDepth();

	    b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	    b.pos(0, h, 0).tex(u1, v2).endVertex();
	    b.pos(w, h, 0).tex(u2, v2).endVertex();
	    b.pos(w, 0, 0).tex(u2, v1).endVertex();
	    b.pos(0, 0, 0).tex(u1, v1).endVertex();
	    t.draw();

	    GlStateManager.enableDepth();
	    GlStateManager.disableBlend();
	}
	
	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event) {
	    event.getMap().registerSprite(
	        new ResourceLocation(Reference.MODID, "gui/blue_fire_layer_0")
	    );
	}
}