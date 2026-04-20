package net.chaos.chaosmod.entity.animal.render;

import net.chaos.chaosmod.entity.animal.EntityVulture;
import net.chaos.chaosmod.entity.animal.model.ModelVulture;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class RenderVulture extends RenderLiving<EntityVulture> {

	public static final ResourceLocation TEXTURES[] = new ResourceLocation[] {
		new ResourceLocation(Reference.MODID, "textures/entity/animals/vulture/vulture.png")
	};

	public RenderVulture(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelVulture(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityVulture entity) {
		return TEXTURES[0];
	}
}