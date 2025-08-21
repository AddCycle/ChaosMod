package net.chaos.chaosmod.world.events;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
    public static void onRenderOverlayEvent(RenderGameOverlayEvent.Post event) {
		if (event.getType() != RenderGameOverlayEvent.ElementType.TEXT) return;

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

                mc.fontRenderer.drawStringWithShadow(info, x - mc.fontRenderer.getStringWidth(info) / 2, y, 0xFFFFFF);
                mc.fontRenderer.drawStringWithShadow(modName, x - mc.fontRenderer.getStringWidth(modName) / 2, y + 10, Colors.BLUE.getRGB());


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

                mc.fontRenderer.drawStringWithShadow(info, x - mc.fontRenderer.getStringWidth(info) / 2, y, 0xFFFFFF);
                mc.fontRenderer.drawStringWithShadow(modName, x - mc.fontRenderer.getStringWidth(modName) / 2, y + 10, Colors.BLUE.getRGB());
            }
        }
    }
}
