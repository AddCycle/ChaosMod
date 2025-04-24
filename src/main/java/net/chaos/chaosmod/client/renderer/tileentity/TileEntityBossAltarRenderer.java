package net.chaos.chaosmod.client.renderer.tileentity;

import net.chaos.chaosmod.client.model.BossAltarModel;
import net.chaos.chaosmod.tileentity.TileEntityBossAltar;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class TileEntityBossAltarRenderer extends TileEntitySpecialRenderer<TileEntityBossAltar> {
	private static final BossAltarModel model = new BossAltarModel();
    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/tileentity/boss_altar.png");

    @Override
    public void render(TileEntityBossAltar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 0.5, y + 1.5, z + 0.5);
        GlStateManager.rotate(180F, 0F, 0F, 1F);

        // animation maybe move it into another class later FIXME
        if (te.isAnimating) {
            float progress = (te.duration - te.animationTicks + partialTicks) / te.duration; // 0 → 1
            // model.base_group.rotateAngleY = progress * (float)Math.PI * 8;
            // model.spikes.rotateAngleY = progress * (float)Math.PI * 8;
            // model.spikes.offsetY = - progress * 3;
            model.spikes.offsetY = -progress * 2;
            model.spike_1.offsetZ = progress * 3;
            model.spike_1.rotateAngleY = progress * (float)Math.PI * 8;
            model.spike_2.offsetZ = -progress * 3;
            model.spike_2.rotateAngleY = progress * (float)Math.PI * 8;
            model.spike_3.offsetX = progress * 3;
            model.spike_3.rotateAngleY = progress * (float)Math.PI * 8;
            model.spike_4.offsetX = -progress * 3;
            model.spike_4.rotateAngleY = progress * (float)Math.PI * 8;
            model.walls.rotateAngleY = progress * (float)Math.PI * 8;
            model.walls.offsetY = -progress * 0.5f;
        } else {
            model.spikes.offsetY = 0;
        	model.spike_1.offsetZ = 0;
        	model.spike_1.offsetX = 0;
        	model.spike_1.rotateAngleY = 0;
        	model.spike_2.offsetZ = 0;
        	model.spike_2.offsetX = 0;
        	model.spike_2.rotateAngleY = 0;
        	model.spike_3.offsetX = 0;
        	model.spike_3.offsetZ = 0;
        	model.spike_3.rotateAngleY = 0;
        	model.spike_4.offsetX = 0;
        	model.spike_4.offsetZ = 0;
        	model.spike_4.rotateAngleY = 0;
            model.base_group.rotateAngleY = 0;
            model.spikes.rotateAngleX = 0;
            model.spikes.rotateAngleY = 0;
            model.spikes.rotateAngleZ = 0;
            model.spikes.offsetY = 0;
            model.spikes.offsetZ = 0;
            model.walls.rotateAngleZ = 0;
            model.walls.rotateAngleX = 0;
            model.walls.rotateAngleY = 0;
            model.walls.offsetX = 0;
            model.walls.offsetY = 0;
            model.walls.offsetZ = 0;
        }
        /*float time = te.getWorld().getTotalWorldTime() + partialTicks;
        model.global.rotateAngleY = time * 0.1F;  // spin it*/
        bindTexture(TEXTURE);
        model.render(0.0625f);
        GlStateManager.popMatrix();
    }
}
