package net.chaos.chaosmod.entity.animal;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollow;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityVulture extends EntityFlying implements net.minecraft.entity.passive.EntityFlying {

	public EntityVulture(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void initEntityAI() {
//        this.tasks.addTask(0, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
//        this.tasks.addTask(2, this.aiSit);
//        this.tasks.addTask(2, new EntityAIFollowOwnerFlying(this, 1.0D, 5.0F, 1.0F));
//        this.tasks.addTask(2, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
//        this.tasks.addTask(3, new EntityAILandOnOwnersShoulder(this));
        this.tasks.addTask(3, new EntityAIFollow(this, 1.0D, 3.0F, 7.0F));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.4000000059604645D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
	}
}