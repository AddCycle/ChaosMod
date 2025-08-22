package net.chaos.chaosmod.world.events;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.chaos.chaosmod.config.ModConfig;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Colors;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class AdditionalOverlayInfosEvents {
	
	@SubscribeEvent
    public static void onRenderOverlayEvent(RenderGameOverlayEvent.Text event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.ALL && !ModConfig.CLIENT.areAdditionalInfosEnabled) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        World world = mc.world;

        if (player == null || world == null) return;

        // Reach distance of player
        double reach = mc.playerController.getBlockReachDistance();
        Vec3d eyePos = player.getPositionEyes(1.0f);
        Vec3d lookVec = player.getLook(1.0f);
        Vec3d reachVec = eyePos.addVector(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach);

        // raytrace blocks
        RayTraceResult blockResult = world.rayTraceBlocks(eyePos, reachVec, false, false, true);

        // raytrace entities
        Entity lookedEntity = null;
        double closestDistance = reach;

        AxisAlignedBB searchBox = player.getEntityBoundingBox()
                .expand(lookVec.x * reach, lookVec.y * reach, lookVec.z * reach)
                .grow(1.0D); // grow a bit so thin entities aren’t skipped

        List<Entity> entities = world.getEntitiesInAABBexcluding(player, searchBox,
                e -> e != null && e.canBeCollidedWith());

        for (Entity entity : entities) {
            AxisAlignedBB entityBB = entity.getEntityBoundingBox().grow(0.3D); // pad hitbox
            RayTraceResult intercept = entityBB.calculateIntercept(eyePos, reachVec);

            if (entityBB.contains(eyePos)) {
                if (closestDistance >= 0.0D) {
                    lookedEntity = entity;
                    closestDistance = 0.0D;
                }
            } else if (intercept != null) {
                double distToEntity = eyePos.distanceTo(intercept.hitVec);
                if (distToEntity < closestDistance) {
                    lookedEntity = entity;
                    closestDistance = distToEntity;
                }
            }
        }

        // Choosing closest (entity vs block)
        RayTraceResult finalResult = null;
        if (lookedEntity != null &&
            (blockResult == null || closestDistance < eyePos.distanceTo(blockResult.hitVec))) {
            finalResult = new RayTraceResult(lookedEntity);
        } else if (blockResult != null) {
            finalResult = blockResult;
        }

        // Draw overlay if something is hit
        if (finalResult != null) {
            int x = event.getResolution().getScaledWidth() / 2;
            int y = 10;
            int width = 80;
            int height = 40;
            int offsetY = 10;
            int color = 0x800000a4;

            if (finalResult.typeOfHit == RayTraceResult.Type.BLOCK) {
                BlockPos pos = finalResult.getBlockPos();
                IBlockState state = world.getBlockState(pos);
                Block block = state.getBlock();

                String info = new TextComponentTranslation(block.getLocalizedName()).getFormattedText();
                ResourceLocation rl = block.getRegistryName();

                String modId = "unknown";
                String modName = "Unknown Mod";
                if (rl != null) {
                    modId = rl.getResourceDomain();

                	// Lookup mod container
                    ModContainer container = Loader.instance().getIndexedModList().get(modId);
                    if (container != null) {
                        modName = container.getName(); // Nice formatted mod name
                    } else {
                        // fallback: just capitalize the modid
                        modName = Character.toUpperCase(modId.charAt(0)) + modId.substring(1);
                    }
                }
                
                if (ModConfig.CLIENT.isAdditionalInfosBackgroundEnabled) drawColoredRect(x - width / 2, y - height / 2 + offsetY, width, height, color);

                mc.fontRenderer.drawStringWithShadow(info, x - mc.fontRenderer.getStringWidth(info) / 2, y, 0xFFFFFF);
                GlStateManager.color(1, 1, 1, 1);

                mc.fontRenderer.drawStringWithShadow(modName, x - mc.fontRenderer.getStringWidth(modName) / 2, y + 10, Colors.BLUE.getRGB());
                GlStateManager.color(1, 1, 1, 1);

            } else if (finalResult.typeOfHit == RayTraceResult.Type.ENTITY) {
            	Entity entity = finalResult.entityHit;

                // Display name
                String info = entity.getDisplayName().getFormattedText();

                // Registry name lookup
                ResourceLocation rl = EntityList.getKey(entity);
                String modId = "unknown";
                String modName = "Unknown Mod";
                if (rl != null) {
                    modId = rl.getResourceDomain();

                	// Lookup mod container
                    ModContainer container = Loader.instance().getIndexedModList().get(modId);
                    if (container != null) {
                        modName = container.getName(); // Nice formatted mod name
                    } else {
                        // fallback: just capitalize the modid
                        modName = Character.toUpperCase(modId.charAt(0)) + modId.substring(1);
                    }
                }
                
                if (ModConfig.CLIENT.isAdditionalInfosBackgroundEnabled) drawColoredRect(x - width / 2, y - height / 2 + offsetY, width, height, color);

                mc.fontRenderer.drawStringWithShadow(info, x - mc.fontRenderer.getStringWidth(info) / 2, y, 0xFFFFFF);
                GlStateManager.color(1, 1, 1, 1);

                mc.fontRenderer.drawStringWithShadow(modName, x - mc.fontRenderer.getStringWidth(modName) / 2, y + 10, Colors.BLUE.getRGB());
                GlStateManager.color(1, 1, 1, 1);
            }
        }
    }
	
	public static void drawColoredRect(int x, int y, int width, int height, int argb) {
	    Tessellator tess = Tessellator.getInstance();
	    BufferBuilder buffer = tess.getBuffer();

	    float a = (argb >> 24 & 255) / 255.0F;
	    float r = (argb >> 16 & 255) / 255.0F;
	    float g = (argb >> 8  & 255) / 255.0F;
	    float b = (argb       & 255) / 255.0F;

	    GlStateManager.pushMatrix();
	    GlStateManager.disableTexture2D();
	    GlStateManager.enableBlend();
	    GlStateManager.tryBlendFuncSeparate(
	        GlStateManager.SourceFactor.SRC_ALPHA,
	        GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
	        GlStateManager.SourceFactor.ONE,
	        GlStateManager.DestFactor.ZERO
	    );

	    buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	    buffer.pos(x,         y + height, 0).color(r, g, b, a).endVertex();
	    buffer.pos(x + width, y + height, 0).color(r, g, b, a).endVertex();
	    buffer.pos(x + width, y,          0).color(r, g, b, a).endVertex();
	    buffer.pos(x,         y,          0).color(r, g, b, a).endVertex();
	    tess.draw();

	    GlStateManager.disableBlend();
	    GlStateManager.enableTexture2D();
	    GlStateManager.color(1f, 1f, 1f, 1f);
	    GlStateManager.popMatrix();
	    GlStateManager.color(1f, 1f, 1f, 1f);
	}
}
