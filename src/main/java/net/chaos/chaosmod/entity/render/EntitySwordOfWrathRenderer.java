package net.chaos.chaosmod.entity.render;

import net.chaos.chaosmod.entity.EntitySwordOfWrath;
import net.chaos.chaosmod.entity.model.SwordOfWrathModel;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EntitySwordOfWrathRenderer extends RenderLiving<EntitySwordOfWrath> {
	public static final ResourceLocation SWORD_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/sword_of_wrath.png");

	public EntitySwordOfWrathRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new SwordOfWrathModel(), 1.0f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySwordOfWrath entity) {
		return SWORD_TEXTURE;
	}
}