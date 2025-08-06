package net.chaos.chaosmod.world.events;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModDamageSources;
import net.chaos.chaosmod.network.PacketShowFireOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import util.Reference;

public class PlayerFireRenderHandler {

	private static final ResourceLocation BLUE_FIRE_LAYER_0 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_0.png");
	private static final ResourceLocation BLUE_FIRE_LAYER_1 = new ResourceLocation(Reference.MODID, "textures/entity/boss/blue_fire_layer_0.png");
	private static final ResourceLocation GUI_BLUE_FIRE_OVERLAY_1 = new ResourceLocation(Reference.MODID, "textures/gui/blue_fire_layer_0.png"); // animated
	private static final ResourceLocation GUI_BLUE_FIRE_OVERLAY_2 = new ResourceLocation(Reference.MODID, "textures/gui/blue_fire_layer_1.png"); // animated

	@SubscribeEvent
	public void onRenderPlayer(RenderPlayerEvent.Pre event) {
		EntityPlayer player = event.getEntityPlayer();
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
		GlStateManager.depthMask(true);

		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}
	
	
	private void renderCustomFire(Entity entity, float partialTicks) {
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
	public void onRenderOverlay(RenderBlockOverlayEvent event) {
		if (event.getOverlayType() == RenderBlockOverlayEvent.OverlayType.FIRE) {
			event.setCanceled(true);
            Minecraft mc = Minecraft.getMinecraft();
//			System.out.println(mc.player.getEntityData().getBoolean("ShowCustomFireOverlay"));

            if (mc.player.getEntityData().getBoolean("ShowCustomFireOverlay")) {
            	Main.getLogger().info("Player on fire rendering blue fire overlay : {}", event.getPlayer());
                renderFireInFirstPerson(mc);
            }
//            renderFireInFirstPerson(mc);
		}
	}
	
	@SubscribeEvent
	public void onPlayerHurt(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (event.getSource() == ModDamageSources.BLUE_FIRE) {
				int seconds = 100;
				int ticks = seconds * 20;
				boolean shouldShow = true;
				Main.network.sendTo(new PacketShowFireOverlay(shouldShow, ticks), (EntityPlayerMP) player);
				Main.getLogger().info("Packet sent with blue fire rendering, {}", event.getSource());
			}
		}
	}
	
	@SubscribeEvent
	public void onClientTick(TickEvent.PlayerTickEvent event) {
	    if (event.player.getEntityData().getBoolean("ShowCustomFireOverlay")) {
            Main.getLogger().info("Boolean got blue_fire : rendering...");
	        int ticks = event.player.getEntityData().getInteger("CustomFireOverlayTicks");
	        if (ticks > 0) {
	            event.player.getEntityData().setInteger("CustomFireOverlayTicks", ticks - 1);
	        } else {
	            event.player.getEntityData().setBoolean("ShowCustomFireOverlay", false);
	        }
	    }
	}

    private void renderFireInFirstPerson(Minecraft mc)
    {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.9F);
        GlStateManager.depthFunc(519);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        float f = 1.0F;

        for (int i = 0; i < 2; ++i)
        {
            GlStateManager.pushMatrix();
            TextureAtlasSprite textureatlassprite = mc.getTextureMapBlocks().getAtlasSprite("chaosmod:gui/blue_fire_layer_0");
            mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            float f1 = textureatlassprite.getMinU();
            float f2 = textureatlassprite.getMaxU();
            float f3 = textureatlassprite.getMinV();
            float f4 = textureatlassprite.getMaxV();
            float f5 = -0.5F;
            float f6 = 0.5F;
            float f7 = -0.5F;
            float f8 = 0.5F;
            float f9 = -0.5F;
            GlStateManager.translate((float)(-(i * 2 - 1)) * 0.24F, -0.3F, 0.0F);
            GlStateManager.rotate((float)(i * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(-0.5D, -0.5D, -0.5D).tex((double)f2, (double)f4).endVertex();
            bufferbuilder.pos(0.5D, -0.5D, -0.5D).tex((double)f1, (double)f4).endVertex();
            bufferbuilder.pos(0.5D, 0.5D, -0.5D).tex((double)f1, (double)f3).endVertex();
            bufferbuilder.pos(-0.5D, 0.5D, -0.5D).tex((double)f2, (double)f3).endVertex();
            tessellator.draw();
            GlStateManager.popMatrix();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.depthMask(true);
        GlStateManager.depthFunc(515);
    }
    
    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(new ResourceLocation(Reference.MODID, "gui/blue_fire_layer_0"));
    }

}
