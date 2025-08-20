package net.chaos.chaosmod.entity.render;

import net.chaos.chaosmod.entity.EntityBipedBasic;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EntityBipedBasicRenderer extends RenderLiving<EntityBipedBasic> {
	private static final ResourceLocation STEVE = new ResourceLocation("textures/entity/steve.png");

	public EntityBipedBasicRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelPlayer(0.0f, false), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBipedBasic entity) {
		return STEVE;
	}
	
	@Override
	protected boolean canRenderName(EntityBipedBasic entity) {
		return false;
	}
}
