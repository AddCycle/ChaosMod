package net.chaos.chaosmod.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class LittleGiantEntity extends EntityMob {

	public LittleGiantEntity(World worldIn) {
		super(worldIn);
		this.setSize(0.5f, 1.5f);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		// this.tasks.addTask(1, new EntityAIMoveTowardsTarget(this, 1.0, 20f)); // no need to move towards it when melee
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
		this.targetTasks.addTask(0, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityViking>(this, EntityViking.class, false));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<EntityPicsou>(this, EntityPicsou.class, false));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(world.getDifficulty().getDifficultyId() == 1 ? 40.0D : 80.0D); // easy, normal | hard | >
	    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
	    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}

}
