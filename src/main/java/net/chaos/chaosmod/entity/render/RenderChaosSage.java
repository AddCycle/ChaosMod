package net.chaos.chaosmod.entity.render;

import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.entity.model.ChaosSageModel;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class RenderChaosSage extends RenderLiving<EntityChaosSage> {

	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID, "textures/entity/chaos_sage.png");

	public RenderChaosSage(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ChaosSageModel(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityChaosSage entity) {
		return TEXTURES;
	}

}
