package util.blockstates;

import java.awt.Color;

import net.chaos.chaosmod.config.ModConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class RenderBlockOutlinesEvent {

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onBlockHighlight(DrawBlockHighlightEvent event) {
	    if (event.getTarget().typeOfHit != RayTraceResult.Type.BLOCK) return;

	    World world = event.getPlayer().getEntityWorld();
	    BlockPos pos = event.getTarget().getBlockPos();

	    IBlockState state = world.getBlockState(pos);
	    AxisAlignedBB box = state.getSelectedBoundingBox(world, pos);

	    if (box == null) return;

	    // Cancel default block outline
	    event.setCanceled(true);

	    // Interpolate view position
	    double partialTicks = event.getPartialTicks();
	    Entity player = event.getPlayer();
	    double dx = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
	    double dy = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
	    double dz = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;

	    AxisAlignedBB shifted = box.offset(-dx, -dy, -dz);
	    Color color = new Color(ModConfig.outline_color);
	    // System.out.println(color.getRGB());

	    // Draw red bounding box
	    GlStateManager.pushMatrix();
	    GlStateManager.disableTexture2D();
	    GlStateManager.enableBlend();
	    // GlStateManager.disableDepth();
	    GlStateManager.glLineWidth(20.0F);

	    RenderGlobal.drawSelectionBoundingBox(shifted, color.getRed(), color.getGreen(), color.getBlue(), 0.0F);

	    // GlStateManager.enableDepth();
	    GlStateManager.disableBlend();
	    GlStateManager.enableTexture2D();
	    GlStateManager.popMatrix();
	}
}
