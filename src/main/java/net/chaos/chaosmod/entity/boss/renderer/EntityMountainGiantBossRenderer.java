package net.chaos.chaosmod.entity.boss.renderer;

import net.chaos.chaosmod.entity.boss.entities.EntityMountainGiantBoss;
import net.chaos.chaosmod.entity.boss.model.MountainGiantModel;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EntityMountainGiantBossRenderer extends RenderLiving<EntityMountainGiantBoss>{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/boss/mountain_giant.png");

	public EntityMountainGiantBossRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new MountainGiantModel(), 0.5f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMountainGiantBoss entity) {
		return TEXTURE;
	}

}
