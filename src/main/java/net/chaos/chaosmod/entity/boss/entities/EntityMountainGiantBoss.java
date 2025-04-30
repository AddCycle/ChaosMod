package net.chaos.chaosmod.entity.boss.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
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
	}
	
	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 20.0f));
		this.tasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
		super.initEntityAI();
	}
	
	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}

}
