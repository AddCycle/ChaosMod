package net.chaos.chaosmod.world.events;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(value = Side.CLIENT, modid = Reference.MODID)
public class PlayerFireRenderHandler {

	private static final ResourceLocation BLUE_FIRE_LAYER_0 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_0.png");
	private static final ResourceLocation BLUE_FIRE_LAYER_1 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_0.png");

	@SubscribeEvent
	public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
		EntityPlayer player = event.getEntityPlayer();
		Minecraft mc = Minecraft.getMinecraft();
		if (!player.getEntityData().getBoolean("ShowCustomFireOverlay") || player.isPlayerSleeping()) return;
		Main.getLogger().debug("Player on fire rendering blue fire : {}", event.getEntityPlayer());

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		// Translate to player position
		double x = event.getX();
		double y = event.getY();
		double z = event.getZ();

		GlStateManager.translate(x, y, z);
		GlStateManager.depthMask(false);
		renderCustomFire(player, event.getPartialRenderTick());
		// renderFireInFirstPerson(mc);
		GlStateManager.depthMask(true);

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
	
	
	private static void renderCustomFire(Entity entity, float partialTicks) {
	    GlStateManager.disableLighting();
	    GlStateManager.pushMatrix();

	    float scale = entity.width * 1.4F;
	    GlStateManager.scale(scale, scale, scale);

	    Tessellator tessellator = Tessellator.getInstance();
	    BufferBuilder buffer = tessellator.getBuffer();

	    float width = 0.5F;
	    float depth = 0.0F;
	    float height = entity.height / scale;
	    float yOffset = (float) (entity.posY - entity.getEntityBoundingBox().minY);

	    GlStateManager.rotate(-Minecraft.getMinecraft().getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
	    GlStateManager.translate(0.0F, 0.0F, -0.3F + (float)((int)height) * 0.02F);
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

	    final int totalFrames = 32; // assuming 512px height / 16px = 32 animation frames
	    final float frameHeight = 1.0F / totalFrames;
	    float animationSpeed = 2.0F;

	    int ageInTicks = entity.ticksExisted;
	    float frameFloat = (ageInTicks + partialTicks) * animationSpeed;
	    int currentFrame = (int) frameFloat % totalFrames;

	    while (height > 0.0F) {
	        // Flicker between two layers
	        ResourceLocation currentTexture = ((ageInTicks / 2) % 2 == 0) ? BLUE_FIRE_LAYER_0 : BLUE_FIRE_LAYER_1;
	        Minecraft.getMinecraft().getTextureManager().bindTexture(currentTexture);

	        float uMin = 0.0F;
	        float uMax = 1.0F;
	        float vMin = frameHeight * currentFrame;
	        float vMax = vMin + frameHeight;

	        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	        buffer.pos( width, 0.0F - yOffset, depth).tex(uMax, vMax).endVertex();
	        buffer.pos(-width, 0.0F - yOffset, depth).tex(uMin, vMax).endVertex();
	        buffer.pos(-width, 1.4F - yOffset, depth).tex(uMin, vMin).endVertex();
	        buffer.pos( width, 1.4F - yOffset, depth).tex(uMax, vMin).endVertex();
	        tessellator.draw();

	        height -= 0.45F;
	        yOffset -= 0.45F;
	        width *= 0.9F;
	        depth += 0.03F;
	    }

	    GlStateManager.popMatrix();
	    GlStateManager.enableLighting();
	}
	
	@SubscribeEvent
	public static void onRenderOverlay(RenderGameOverlayEvent.Post event) {
	    if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

	    Minecraft mc = Minecraft.getMinecraft();
	    EntityPlayer player = mc.player;
	    if (player == null) return;

	    if (!player.getEntityData().getBoolean("ShowCustomFireOverlay") || player.isPlayerSleeping())
	        return;

	    renderFireInFirstPerson(mc, player);
	}

	private static void renderFireInFirstPerson(Minecraft mc, EntityPlayer player) {
	    Tessellator tessellator = Tessellator.getInstance();
	    BufferBuilder buffer = tessellator.getBuffer();

	    GlStateManager.disableDepth();
	    GlStateManager.depthMask(false);
	    GlStateManager.enableBlend();
	    GlStateManager.tryBlendFuncSeparate(
	            GlStateManager.SourceFactor.SRC_ALPHA,
	            GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
	            GlStateManager.SourceFactor.ONE,
	            GlStateManager.DestFactor.ZERO
	    );
	    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

	    // Two layers (like vanilla flickering fire)
	    for (int i = 0; i < 2; ++i) {
	        GlStateManager.pushMatrix();

	        ResourceLocation tex = (i % 2 == 0) ? BLUE_FIRE_LAYER_0 : BLUE_FIRE_LAYER_1;
	        mc.getTextureManager().bindTexture(tex);

	        float f = (i % 2) * 0.5F; // offset
	        float w = mc.displayWidth;
	        float h = mc.displayHeight;

	        // Translate/rotate like vanilla
	        GlStateManager.translate((float)(-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
	        GlStateManager.rotate((i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);

	        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
	        buffer.pos(-1.0D, -1.0D, -0.5D).tex(0.0D, 1.0D).endVertex();
	        buffer.pos( 1.0D, -1.0D, -0.5D).tex(1.0D, 1.0D).endVertex();
	        buffer.pos( 1.0D,  1.0D, -0.5D).tex(1.0D, 0.0D).endVertex();
	        buffer.pos(-1.0D,  1.0D, -0.5D).tex(0.0D, 0.0D).endVertex();
	        tessellator.draw();

	        GlStateManager.popMatrix();
	    }

	    GlStateManager.disableBlend();
	    GlStateManager.depthMask(true);
	    GlStateManager.enableDepth();
	}
    
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(new ResourceLocation(Reference.MODID, "gui/blue_fire_layer_0"));
    }

}