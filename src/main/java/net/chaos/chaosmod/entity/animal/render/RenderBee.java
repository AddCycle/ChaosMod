package net.chaos.chaosmod.entity.animal.render;

import net.chaos.chaosmod.entity.animal.EntityBee;
import net.chaos.chaosmod.entity.animal.model.ModelBee;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class RenderBee extends RenderLiving<EntityBee> {
	    private static final ResourceLocation ORANGE_BEE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/animals/bear/orange_bear.png");
    private static final ResourceLocation YELLOW_BEE_TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/animals/bee/bee.png");
	    private static final ResourceLocation YELLOW_BEE_ANGRY = new ResourceLocation(Reference.MODID, "textures/entity/animals/bee/bee_angry.png");

	public RenderBee(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelBee(), 0.4f);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBee entity) {
		return entity.isAngry() ? YELLOW_BEE_ANGRY : YELLOW_BEE_TEXTURE;
	}
	
    public void doRender(EntityBee entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected void preRenderCallback(EntityBee entitylivingbaseIn, float partialTickTime)
    {
//        GlStateManager.scale(1.2F, 1.2F, 1.2F);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

}