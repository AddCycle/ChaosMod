package net.chaos.chaosmod.entity.animal.render;

import net.chaos.chaosmod.entity.animal.EntityBear;
import net.chaos.chaosmod.entity.animal.EntityBear.EnumVariant;
import net.chaos.chaosmod.entity.animal.layers.LayerBearArmor;
import net.chaos.chaosmod.entity.animal.model.ModelBear;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class RenderBear extends RenderLiving<EntityBear> {
    private static final ResourceLocation ORANGE_BEAR_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/animals/bear/orange_bear.png");
    private static final ResourceLocation BROWN_BEAR_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/animals/bear/teddy_bear.png");

	public RenderBear(RenderManager renderManager) {
		super(renderManager, new ModelBear(), 0.7F);
		this.addLayer(new LayerBearArmor(this));
//		this.addLayer(new LayerHeldItem(this)); TODO : make a custom layer renderer
	}

    protected ResourceLocation getEntityTexture(EntityBear entity)
    {
    	EnumVariant variant = EnumVariant.values()[entity.getVariant()];
    	switch (variant) {
			case BROWN:
				return BROWN_BEAR_TEXTURE;
			case ORANGE:
				return ORANGE_BEAR_TEXTURE;
			default:
				return BROWN_BEAR_TEXTURE;
    	}
    }

    public void doRender(EntityBear entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected void preRenderCallback(EntityBear entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scale(1.2F, 1.2F, 1.2F);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

}
