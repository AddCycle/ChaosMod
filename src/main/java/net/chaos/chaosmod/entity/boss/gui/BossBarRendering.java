package net.chaos.chaosmod.entity.boss.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(Side.CLIENT)
public class BossBarRendering {
	public static int flag = 0;
	public static final ResourceLocation BLAZE_PHASE_1 = new ResourceLocation(Reference.MODID, "textures/gui/entity/boss/revenge_blaze_bar_1.png");
	public static final ResourceLocation BLAZE_PHASE_2 = new ResourceLocation(Reference.MODID, "textures/gui/entity/boss/revenge_blaze_bar_2.png");
	public static final ResourceLocation GIANT_PHASE_1 = new ResourceLocation(Reference.MODID, "textures/gui/entity/boss/mountain_giant_bar_1.png");
	public static final ResourceLocation GIANT_PHASE_2 = new ResourceLocation(Reference.MODID, "textures/gui/entity/boss/mountain_giant_bar_2.png");
	public static final ResourceLocation EYE_PHASE_1 = new ResourceLocation(Reference.MODID, "textures/gui/entity/boss/eye_bar_1.png");
	public static final ResourceLocation EYE_PHASE_2 = new ResourceLocation(Reference.MODID, "textures/gui/entity/boss/eye_bar_2.png");
	public static final ResourceLocation MASTER_1 = new ResourceLocation(Reference.MODID, "textures/gui/entity/boss/master_bar_1.png");
	public static final ResourceLocation MASTER_2 = new ResourceLocation(Reference.MODID, "textures/gui/entity/boss/master_bar_2.png");

	@SubscribeEvent
	public void onRenderBossBar(RenderGameOverlayEvent.BossInfo event) {
	    if (!(event.getBossInfo() instanceof BossInfo)) {
	        return;
	    }

	    BossInfo bossInfo = event.getBossInfo();

	    if (!bossInfo.getName().getUnformattedText().contains(new TextComponentTranslation("entity.revenge_blaze_boss.name").getUnformattedText())
	    && (!bossInfo.getName().getUnformattedText().contains(new TextComponentTranslation("entity.mountain_giant_boss.name").getUnformattedText()))
	    && (!bossInfo.getName().getUnformattedText().contains(new TextComponentTranslation("entity.eye_of_truth.name").getUnformattedText()))
	    && (!bossInfo.getName().getUnformattedText().contains(new TextComponentTranslation("entity.chaos_master.name").getUnformattedText()))) {
	    	return;
	    }

	    // Cancel vanilla rendering
	    event.setCanceled(true);

	    Minecraft mc = Minecraft.getMinecraft();
	    ScaledResolution scaledRes = new ScaledResolution(mc);
	    int width = scaledRes.getScaledWidth();
	    
	    if (bossInfo.getName().getUnformattedText().contains(new TextComponentTranslation("entity.revenge_blaze_boss.name").getUnformattedText())) {
	        mc.getTextureManager().bindTexture((bossInfo.getPercent()) >= 0.5f ? BLAZE_PHASE_1 : BLAZE_PHASE_2);
	    } else if (bossInfo.getName().getUnformattedText().contains(new TextComponentTranslation("entity.mountain_giant_boss.name").getUnformattedText())) {
	    	mc.getTextureManager().bindTexture((bossInfo.getPercent()) >= 0.5f ? GIANT_PHASE_1 : GIANT_PHASE_2);
	    } else if (bossInfo.getName().getUnformattedText().contains(new TextComponentTranslation("entity.eye_of_truth.name").getUnformattedText())) {
	    	mc.getTextureManager().bindTexture((bossInfo.getPercent()) >= 0.5f ? EYE_PHASE_1 : EYE_PHASE_2);
	    } else if (bossInfo.getName().getUnformattedText().contains(new TextComponentTranslation("entity.chaos_master.name").getUnformattedText())) {
	    	mc.getTextureManager().bindTexture((bossInfo.getPercent()) >= 0.5f ? MASTER_1 : MASTER_2);
	    } else {
	    	return;
	    }

	    /*
	     * Change this barWidth, fullHeight in order to match the texture
	     */
	    int barWidth = 182;
	    int fullHeight = 40;
	    int barHeight = 20; // Height of one section
	    int x = (width - barWidth) / 2;
	    int y = 20;
	    int padding_up = 17;// distance from top

	    // Draw background (top 0-20 pixels of the texture)
	    // gui.drawTexturedModalRect(x, y, 0, 0, barWidth, barHeight);

	    // Draw health bar fill (bottom 20-40 pixels of the texture)
	    int healthWidth = (int)(bossInfo.getPercent() * (float)barWidth);
	    if (healthWidth > 0) {
	    	Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 20, healthWidth, barHeight, barWidth, fullHeight);
	    }

	    Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 0, barWidth, barHeight, barWidth, fullHeight);

	    // Draw boss name above
	    String bossName = bossInfo.getName().getFormattedText();
	    int red = 0xFF0000;
	    int blue = 0x0000FF;
	    mc.fontRenderer.drawStringWithShadow(bossName, (width / 2) - (mc.fontRenderer.getStringWidth(bossName) / 2), padding_up - 10, (bossInfo.getPercent()) >= 0.5f ? red : blue);
	}


}
