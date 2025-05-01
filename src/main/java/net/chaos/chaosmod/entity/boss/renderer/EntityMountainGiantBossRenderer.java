package net.chaos.chaosmod.entity.boss.renderer;

import net.chaos.chaosmod.entity.boss.entities.EntityMountainGiantBoss;
import net.chaos.chaosmod.entity.boss.model.MountainGiantModel;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class EntityMountainGiantBossRenderer extends RenderLiving<EntityMountainGiantBoss>{
	public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/boss/mountain_giant.png");

	public EntityMountainGiantBossRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new MountainGiantModel(), 0.5f);
		this.addLayer(new LayerDeathBoss<EntityMountainGiantBoss>(this, 255, 125, 0));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityMountainGiantBoss entity) {
		return TEXTURE;
	}

    @Override
    protected void applyRotations(EntityMountainGiantBoss entityLiving, float p_77043_2_, float rotationYaw,
    		float partialTicks) {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
    	// super.applyRotations(entityLiving, p_77043_2_, rotationYaw, partialTicks);
    }

}
