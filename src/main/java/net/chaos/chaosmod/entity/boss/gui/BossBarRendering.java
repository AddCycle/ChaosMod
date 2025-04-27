package net.chaos.chaosmod.entity.boss.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(Side.CLIENT)
public class BossBarRendering {

	@SubscribeEvent
	public void onRenderBossBar(RenderGameOverlayEvent.BossInfo event) {
	    if (!(event.getBossInfo() instanceof BossInfo)) {
	        return;
	    }

	    BossInfo bossInfo = event.getBossInfo();

	    // If you only want to replace YOUR boss bar:

	    if (!bossInfo.getName().getUnformattedText().contains("Revenge Blaze")) {
	        return;
	    }

	    // Cancel vanilla rendering
	    event.setCanceled(true);

	    Minecraft mc = Minecraft.getMinecraft();
	    ScaledResolution scaledRes = new ScaledResolution(mc);
	    int width = scaledRes.getScaledWidth();
	    
	    mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/entity/boss/revenge_blaze_bar.png"));

	    int barWidth = 182;
	    int barHeight = 20;
	    int x = (width / 2) - (barWidth / 2);
	    int y = 20; // distance from top

	    Gui gui = new Gui();
		// Draw full bar background
	    gui.drawTexturedModalRect(x, y, 0, 0, barWidth, barHeight);

	    // Draw health bar fill
	    int healthWidth = (int)(bossInfo.getPercent() * (float)barWidth);
	    if (healthWidth > 0) {
	        gui.drawTexturedModalRect(x, y, 0, barHeight, healthWidth, barHeight);
	    }

	    // Draw boss name
	    String bossName = bossInfo.getName().getFormattedText();
	    mc.fontRenderer.drawStringWithShadow(bossName, (width / 2) - (mc.fontRenderer.getStringWidth(bossName) / 2), y - 10, 0xFFFFFF);
	}


}
