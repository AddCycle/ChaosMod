package net.chaos.chaosmod.entity;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityForgeGuardian extends EntityIronGolem {
	public World world;

	public EntityForgeGuardian(World worldIn) {
		super(worldIn);
		this.world = worldIn;
		this.noClip = false;
		this.setEntityInvulnerable(true);
		// this.noClip = true;
	}

	@Override
    protected void initEntityAI()
    {
		// super.initEntityAI();
		// on va utiliser l'IA pour face le player
        // this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        // this.tasks.addTask(100, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        // this.tasks.addTask(8, new EntityAILookIdle(this));
        // this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
        // this.tasks.addTask(2, new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
        /*this.tasks.addTask(3, new EntityAIMoveThroughVillage(this, 0.6D, true));
        this.tasks.addTask(5, new EntityAILookAtVillager(this));
        this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 0.6D));
        this.targetTasks.addTask(1, new EntityAIDefendVillage(this));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true, new Predicate<EntityLiving>()
        {
            public boolean apply(@Nullable EntityLiving p_apply_1_)
            {
                return p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper);
            }
        }));*/
    }
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		// super.onUpdate();
	}
}
