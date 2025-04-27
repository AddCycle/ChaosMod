package net.chaos.chaosmod.entity.boss.entities;

import net.minecraft.client.gui.GuiBossOverlay;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityRevengeBlazeBoss extends EntityBlaze {
	private static final DataParameter<Boolean> TRANSFORMED = EntityDataManager.createKey(EntityRevengeBlazeBoss.class, DataSerializers.BOOLEAN);

	public EntityRevengeBlazeBoss(World worldIn) {
		super(worldIn);
	}
	
	@Override
	protected void entityInit() {
	    super.entityInit();
	    this.dataManager.register(TRANSFORMED, Boolean.FALSE);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		super.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200D);
	}
	
	@Override
    public void onLivingUpdate() {
		super.onLivingUpdate();

	    if (!world.isRemote) { // Server side
	        if (this.getHealth() < this.getMaxHealth() / 2 && !this.isTransformed()) {
	            this.dataManager.set(TRANSFORMED, true); // Force sync to client!
	        }
	    }
	    
	    if (this.isTransformed() && world.isRemote) { // Client side
	    	if (this.isDead) return;
	        for (int i = 0; i < 5; ++i) {
	            double offsetX = (this.rand.nextDouble() - 0.5D) * (double)this.width;
	            double offsetY = this.rand.nextDouble() * (double)this.height;
	            double offsetZ = (this.rand.nextDouble() - 0.5D) * (double)this.width;

	            this.world.spawnParticle(
	                EnumParticleTypes.FLAME,
	                this.posX + offsetX,
	                this.posY + offsetY,
	                this.posZ + offsetZ,
	                0.0D, 0.0D, 1.0D
	            );
	        }
	    }
    }

	public boolean isTransformed() {
	    return this.dataManager.get(TRANSFORMED);
	}
	
	@Override
	public boolean canRenderOnFire() {
		return false;
	}

}
