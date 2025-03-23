package net.chaos.chaosmod.entity.render;

import net.chaos.chaosmod.entity.EntityForgeGuardian;
import net.chaos.chaosmod.entity.model.ModelForgeGuardian;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class RenderForgeGuardian extends RenderLiving<EntityForgeGuardian> {

	public static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MODID, "textures/entity/forge_guardian.png");

	public RenderForgeGuardian(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelForgeGuardian(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityForgeGuardian entity) {
		return TEXTURES;
	}

}
