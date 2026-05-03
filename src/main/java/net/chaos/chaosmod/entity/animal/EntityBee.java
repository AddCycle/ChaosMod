package net.chaos.chaosmod.entity.animal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * TODO : make this tameable later on or maybe not if there is too many
 */
public class EntityBee extends EntityAnimal {
	private static final DataParameter<Boolean> ANGRY = EntityDataManager.<Boolean>createKey(EntityBee.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> STINGING = EntityDataManager.<Boolean>createKey(EntityBee.class,
			DataSerializers.BOOLEAN);

	public EntityBee(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.7F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ANGRY, Boolean.valueOf(false));
		this.dataManager.register(STINGING, Boolean.valueOf(false));
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityBee.AIMeleeAttack());
		this.targetTasks.addTask(1, new EntityBee.AIHurtByTarget());
		this.targetTasks.addTask(2, new EntityBee.AIAttackPlayer());
	}

	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
		super.damageEntity(damageSrc, damageAmount);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	public void setAngry(boolean b) {
		this.dataManager.set(ANGRY, Boolean.valueOf(b));
	}

	public boolean isAngry() { return this.dataManager.get(ANGRY).booleanValue(); }

	public void setStinging(boolean b) {
		this.dataManager.set(STINGING, Boolean.valueOf(b));
	}

	public boolean isStinging() { return this.dataManager.get(STINGING).booleanValue(); }
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();

		if (this.getAttackTarget() == null && isAngry()) {
	        setAngry(false);
	    }
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
	}
	
	class AIHurtByTarget extends EntityAIHurtByTarget {
		public AIHurtByTarget() {
			super(EntityBee.this, false);
		}

		public void startExecuting() {
			super.startExecuting();
			EntityBee.this.setAngry(true);

			if (EntityBee.this.isChild()) {
				this.alertOthers();
				this.resetTask();
			}
		}

		protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
			if (creatureIn instanceof EntityBee && !creatureIn.isChild()) {
				super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
			}
		}
	}

	class AIAttackPlayer extends EntityAINearestAttackableTarget<EntityPlayer> {
		public AIAttackPlayer() {
			super(EntityBee.this, EntityPlayer.class, 20, true, true, null);
		}

		public boolean shouldExecute() {
			if (EntityBee.this.isChild()) return false;
			if (EntityBee.this.isAngry()) return super.shouldExecute();

			if (super.shouldExecute()) {
				for (EntityBee bee : EntityBee.this.world.getEntitiesWithinAABB(EntityBee.class,
					EntityBee.this.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D))) {
					if (bee.isChild()) {
						return true;
					}
				}
			}

			EntityBee.this.setAttackTarget((EntityLivingBase) null);
			return false;
		}

		protected double getTargetDistance() { return super.getTargetDistance() * 0.5D; }
	}
	
	class AIMeleeAttack extends EntityAIAttackMelee {
		public AIMeleeAttack() {
			super(EntityBee.this, 1.25D, true);
		}

		protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
			double d0 = this.getAttackReachSqr(p_190102_1_);

			if (p_190102_2_ <= d0 && this.attackTick <= 0) {
				this.attackTick = 20;
				this.attacker.attackEntityAsMob(p_190102_1_);
				EntityBee.this.setStinging(false);
			} else if (p_190102_2_ <= d0 * 2.0D) {
				if (this.attackTick <= 10) {
					EntityBee.this.setStinging(true);
//					EntityBee.this.playStingSound();
				}
			} else {
				this.attackTick = 20;
				EntityBee.this.setStinging(false);
			}
		}

		public void resetTask() {
			EntityBee.this.setStinging(false);
			super.resetTask();
		}

		protected double getAttackReachSqr(EntityLivingBase attackTarget) {
			float reach = EntityBee.this.width * 2.0F + attackTarget.width;
		    return (double)(reach * reach);
		}
	}
}