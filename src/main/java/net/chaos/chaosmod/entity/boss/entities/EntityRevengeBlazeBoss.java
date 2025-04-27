package net.chaos.chaosmod.entity.boss.entities;

import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
        this.tasks.addTask(4, new EntityRevengeBlazeBoss.AIFireballAttack(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		// super.initEntityAI();
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1024.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.90000000417232513D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
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

    public static class AIFireballAttack extends EntityAIBase
        {
            private final EntityBlaze blaze;
            private int attackStep;
            private int attackTime;

            public AIFireballAttack(EntityBlaze blazeIn)
            {
                this.blaze = blazeIn;
                this.setMutexBits(3);
            }

            /**
             * Returns whether the EntityAIBase should begin execution.
             */
            public boolean shouldExecute()
            {
                EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
                return entitylivingbase != null && entitylivingbase.isEntityAlive();
            }

            /**
             * Execute a one shot task or start executing a continuous task
             */
            public void startExecuting()
            {
                this.attackStep = 0;
            }

            /**
             * Reset the task's internal state. Called when this task is interrupted by another one
             */
            public void resetTask()
            {
                this.blaze.setOnFire(false);
            }

            /**
             * Keep ticking a continuous task that has already been started
             */
            public void updateTask()
            {
                --this.attackTime;
                EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
                double d0 = this.blaze.getDistanceSq(entitylivingbase);

                if (d0 < 4.0D)
                {
                    if (this.attackTime <= 0)
                    {
                        this.attackTime = 20;
                        this.blaze.attackEntityAsMob(entitylivingbase);
                    }

                    this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
                }
                else if (d0 < this.getFollowDistance() * this.getFollowDistance())
                {
                    double d1 = entitylivingbase.posX - this.blaze.posX;
                    double d2 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (this.blaze.posY + (double)(this.blaze.height / 2.0F));
                    double d3 = entitylivingbase.posZ - this.blaze.posZ;

                    if (this.attackTime <= 0)
                    {
                        ++this.attackStep;

                        if (this.attackStep == 1)
                        {
                            this.attackTime = 60;
                            this.blaze.setOnFire(true);
                        }
                        else if (this.attackStep <= 4)
                        {
                            this.attackTime = 6;
                        }
                        else
                        {
                            this.attackTime = 100;
                            this.attackStep = 0;
                            this.blaze.setOnFire(false);
                        }

                        if (this.attackStep > 1)
                        {
                            float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                            this.blaze.world.playEvent((EntityPlayer)null, 1018, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), 0);

                            for (int i = 0; i < 1; ++i)
                            {
                            	// default speed
                                // EntitySmallBlueFireball entitysmallfireball = new EntitySmallBlueFireball(this.blaze.world, this.blaze, d1 + this.blaze.getRNG().nextGaussian() * (double)f, d2, d3 + this.blaze.getRNG().nextGaussian() * (double)f);
                                EntitySmallBlueFireball entitysmallfireball = new EntitySmallBlueFireball(this.blaze.world, this.blaze, d1 * 0.125, d2 * 0.125f, d3 * 0.125);
                                entitysmallfireball.posY = this.blaze.posY + (double)(this.blaze.height / 2.0F) + 0.5D;
                                this.blaze.world.spawnEntity(entitysmallfireball);
                            }
                        }
                    }

                    this.blaze.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
                }
                else
                {
                    this.blaze.getNavigator().clearPath();
                    this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
                }

                super.updateTask();
            }

            private double getFollowDistance()
            {
                IAttributeInstance iattributeinstance = this.blaze.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
                return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
            }
        }

}
