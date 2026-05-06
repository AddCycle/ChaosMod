package net.chaos.chaosmod.entity.mob.render;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSlime;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class RenderHoneySlime extends RenderSlime {
    private static final ResourceLocation HONEY_SLIME = new ResourceLocation(Reference.MODID, "textures/entity/mob/honey_slime.png");

	public RenderHoneySlime(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntitySlime entity) {
		return HONEY_SLIME;
	}
}