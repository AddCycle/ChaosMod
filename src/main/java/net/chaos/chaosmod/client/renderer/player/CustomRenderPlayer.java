package net.chaos.chaosmod.client.renderer.player;

import net.chaos.chaosmod.client.model.CustomPlayerModel;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CustomRenderPlayer extends RenderPlayer {

	public CustomRenderPlayer(RenderManager renderManager) {
		super(renderManager);
	}
	
	public CustomRenderPlayer(RenderManager renderManager, boolean useSmallArmsIn) {
		super(renderManager, useSmallArmsIn);
		mainModel = new CustomPlayerModel(0.0F, useSmallArmsIn);
	}

	@Override
	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw,
			float partialTicks) {

		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}