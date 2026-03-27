package net.chaos.chaosmod.world.events.overlay;

import java.util.Collection;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.config.ModConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
// TODO : a frozen overlay to make all sorts of effects based on mob attacks, priority to fix
public class OverlayEffectsEvents {
	private static final String BLUE_FIRE_TEXTURE = "gui/blue_fire_layer_0";
	private static final ResourceLocation ICONS = new ResourceLocation("textures/gui/icons.png");

	@SubscribeEvent
	public static void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != ElementType.ALL)
			return;

		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution res = event.getResolution();

		NetHandlerPlayClient nethandlerplayclient = mc.player.connection;
		Collection<NetworkPlayerInfo> list = nethandlerplayclient.getPlayerInfoMap();
		NetworkPlayerInfo info = list.stream().filter(npi -> npi.getGameProfile().getId() == mc.player.getUniqueID())
				.findFirst().orElse(null);

		EntityPlayer player = Minecraft.getMinecraft().player;

		if (player.getEntityData().getBoolean("ShowCustomFireOverlay")) {
			renderBlueFire(res, 0.7F);
		}

		if (ModConfig.CLIENT.showPing)
			renderPingIcon(res, info);
	}

	private static void renderPingIcon(ScaledResolution res, NetworkPlayerInfo info) {
		if (info == null)
			return;

		GlStateManager.pushMatrix();
		GlStateManager.enableAlpha();

		int x = (int) (res.getScaledWidth() * 0.95f);
		drawPing(x, 10, info);

		GlStateManager.popMatrix();

	}

	private static void drawPing(int x, int y, NetworkPlayerInfo networkPlayerInfoIn) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(ICONS);
		int j;

		if (networkPlayerInfoIn.getResponseTime() < 0) {
			j = 5;
		} else if (networkPlayerInfoIn.getResponseTime() < 150) {
			j = 0;
		} else if (networkPlayerInfoIn.getResponseTime() < 300) {
			j = 1;
		} else if (networkPlayerInfoIn.getResponseTime() < 600) {
			j = 2;
		} else if (networkPlayerInfoIn.getResponseTime() < 1000) {
			j = 3;
		} else {
			j = 4;
		}

		drawTexturedModalRect(x, y, 0, 176 + j * 8, 10, 8);
	}

	public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (x + 0), (double) (y + height), (double) 0)
				.tex((double) ((float) (textureX + 0) * 0.00390625F),
						(double) ((float) (textureY + height) * 0.00390625F))
				.endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), (double) 0)
				.tex((double) ((float) (textureX + width) * 0.00390625F),
						(double) ((float) (textureY + height) * 0.00390625F))
				.endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + 0), (double) 0)
				.tex((double) ((float) (textureX + width) * 0.00390625F),
						(double) ((float) (textureY + 0) * 0.00390625F))
				.endVertex();
		bufferbuilder.pos((double) (x + 0), (double) (y + 0), (double) 0)
				.tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F))
				.endVertex();
		tessellator.draw();
	}

	private static void renderFireOverlay2D(ScaledResolution res, float alpha) {
		Minecraft mc = Minecraft.getMinecraft();

		mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		TextureAtlasSprite sprite = mc.getTextureMapBlocks().getAtlasSprite("minecraft:blocks/fire_layer_0");

		float u1 = sprite.getMinU();
		float u2 = sprite.getMaxU();
		float v1 = sprite.getMinV();
		float v2 = sprite.getMaxV();

		int w = res.getScaledWidth();
		int h = res.getScaledHeight();

		Tessellator t = Tessellator.getInstance();
		BufferBuilder b = t.getBuffer();

		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();

		GlStateManager.color(1F, 1F, 1F, alpha);

		b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		b.pos(0, h, 0).tex(u1, v2).endVertex();
		b.pos(w, h, 0).tex(u2, v2).endVertex();
		b.pos(w, 0, 0).tex(u2, v1).endVertex();
		b.pos(0, 0, 0).tex(u1, v1).endVertex();
		t.draw();

		GlStateManager.enableDepth();
		GlStateManager.disableBlend();
	}

	private static void renderBlueFire(ScaledResolution res, float alpha) {
		Minecraft mc = Minecraft.getMinecraft();

		boolean flag = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase)mc.getRenderViewEntity()).isPlayerSleeping();
		if (mc.gameSettings.thirdPersonView != 0 || flag) return;

		GlStateManager.pushMatrix();
		mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		TextureAtlasSprite sprite = mc.getTextureMapBlocks().getAtlasSprite(Reference.PREFIX + BLUE_FIRE_TEXTURE);

		float u1 = sprite.getMinU();
		float u2 = sprite.getMaxU();
		float v1 = sprite.getMinV();
		float v2 = sprite.getMaxV();

		int w = res.getScaledWidth();
		int h = res.getScaledHeight();

		Tessellator t = Tessellator.getInstance();
		BufferBuilder b = t.getBuffer();

		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
		GlStateManager.disableDepth();

		GlStateManager.color(1F, 1F, 1F, alpha);

		b.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		b.pos(0, h, 0).tex(u1, v2).endVertex();
		b.pos(w, h, 0).tex(u2, v2).endVertex();
		b.pos(w, 0, 0).tex(u2, v1).endVertex();
		b.pos(0, 0, 0).tex(u1, v1).endVertex();
		t.draw();

		GlStateManager.enableDepth();
		GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}

	@SubscribeEvent
	public static void onTextureStitch(TextureStitchEvent.Pre event) {
		event.getMap().registerSprite(new ResourceLocation(Reference.MODID, "gui/blue_fire_layer_0"));
	}
}