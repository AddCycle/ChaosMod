package net.chaos.chaosmod.entity.render;

import net.chaos.chaosmod.entity.EntityPicsou;
import net.chaos.chaosmod.entity.model.ModelPixou;
import net.chaos.chaosmod.particle.CustomParticleSpawnManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityCreature;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@SideOnly(Side.CLIENT)
public class EntityPicsouRenderer extends RenderLiving<EntityPicsou> {
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MODID, "textures/entity/picsou.png");
	private int tickCounter = 0;

	public EntityPicsouRenderer(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelPixou(), 1.0f);
		// this.addLayer(new LayerHeldItem(this));
		// this.addLayer(new LayerCapeViking(this));
		// this.addLayer(new LayerCreeperCharge(new RenderCreeper(rendermanagerIn)));
	}
	
	@Override
	public void doRender(EntityPicsou entity, double x, double y, double z, float entityYaw, float partialTicks) {
		tickCounter++;
		if (tickCounter >= 100) {
			spawnCoinParticles(entity.getEntityWorld(), entity);
			tickCounter = 0;
		}
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public void spawnCoinParticles(World worldIn, EntityCreature entity)
    {
        double d0 = (double)(-MathHelper.sin(entity.rotationYaw * 0.017453292F));
        double d1 = (double)MathHelper.cos(entity.rotationYaw * 0.017453292F);
        
        if (worldIn.isRemote) CustomParticleSpawnManager.spawnMoneyParticle(worldIn, entity.getPosition(), 0.0D, 1.0D, 0.0D);
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityPicsou entity) {
		return TEXTURE;
	}

}
