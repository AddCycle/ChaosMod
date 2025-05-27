package net.chaos.chaosmod.entity.render;

import net.chaos.chaosmod.entity.LittleGiantEntity;
import net.chaos.chaosmod.entity.model.ModelGiants;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class LittleGiantRenderer extends RenderLiving<LittleGiantEntity> {
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/giants.png");

	public LittleGiantRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelGiants(), 1.0f);
	}

	@Override
	protected ResourceLocation getEntityTexture(LittleGiantEntity entity) {
		return TEXTURE;
	}

}
