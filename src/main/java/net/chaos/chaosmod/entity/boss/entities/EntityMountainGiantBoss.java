package net.chaos.chaosmod.entity.boss.entities;

import net.chaos.chaosmod.entity.ai.EntityAIEscapeWater;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityMountainGiantBoss extends EntityMob {
    public final BossInfoServer bossInfo;

	public EntityMountainGiantBoss(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
		bossInfo = new BossInfoServer(getDisplayName(), Color.BLUE, Overlay.PROGRESS);
		this.bossInfo.setName(getDisplayName());
	}
	
	@Override
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024.0D);
	    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
	    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
	}
	
	@Override
	protected void initEntityAI() {
		// this.tasks.addTask(1, new CustomAITest(this, 1.0f));
		this.tasks.addTask(0, new EntityAISwimming(this));
		// this.tasks.addTask(1, new EntityAIEscapeWater(this, 1.2D));
	    this.tasks.addTask(1, new EntityAIWander(this, 1.0D));
	    this.tasks.addTask(2, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	    this.tasks.addTask(3, new EntityAILookIdle(this));
	    this.tasks.addTask(4, new EntityAIAttackMelee(this, 0.3, false));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
		// super.initEntityAI();
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();
	}
	
	@Override
	public void onLivingUpdate() {
		if (this.deathTime <= 0) { 
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
		super.onLivingUpdate();
	}
	
	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

}
