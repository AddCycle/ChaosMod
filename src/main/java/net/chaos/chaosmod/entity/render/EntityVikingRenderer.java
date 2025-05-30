package net.chaos.chaosmod.entity.render;

import net.chaos.chaosmod.entity.EntityViking;
import net.chaos.chaosmod.entity.model.ModelViking;
import net.chaos.chaosmod.entity.render.layers.LayerCapeViking;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EntityVikingRenderer extends RenderLiving<EntityViking>{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/viking.png");

	public EntityVikingRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelViking(0.0f, false), 1.0f);
		this.addLayer(new LayerHeldItem(this));
		this.addLayer(new LayerCapeViking(this));
		// this.addLayer(new LayerCreeperCharge(new RenderCreeper(rendermanagerIn)));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityViking entity) {
		return TEXTURE;
	}

}
