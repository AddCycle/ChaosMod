package net.chaos.chaosmod.entity.render;

import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.entity.model.ModelPixou;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EntityPicsouRenderer extends RenderLiving<EntityPicsou>{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/picsou.png");

	public EntityPicsouRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelPixou(), 1.0f);
		this.addLayer(new LayerHeldItem(this));
		// this.addLayer(new LayerCapeViking(this));
		// this.addLayer(new LayerCreeperCharge(new RenderCreeper(rendermanagerIn)));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPicsou entity) {
		return TEXTURE;
	}

}
