package net.chaos.chaosmod.entity.animal;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.blocks.BeehiveBlock;
import net.chaos.chaosmod.blocks.BlockRose;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.tileentity.TileEntityBeehive;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.AiMutexBits;

/**
 * FIXME: AI working but needs optimization, maybe run the profiler on some parts of the code
 * REFACTOR: make classes out of this one in order to reuse AI parts
 */
public class EntityBee extends EntityAnimal {
	private static final DataParameter<Boolean> ANGRY = EntityDataManager.<Boolean>createKey(EntityBee.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> STINGING = EntityDataManager.<Boolean>createKey(EntityBee.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> POLLEN = EntityDataManager.<Integer>createKey(EntityBee.class, DataSerializers.VARINT);
	private static final DataParameter<BlockPos> HOME = EntityDataManager.<BlockPos>createKey(EntityBee.class, DataSerializers.BLOCK_POS);

	public EntityBee(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.7F);
        this.moveHelper = new EntityFlyHelper(this);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ANGRY, Boolean.valueOf(false));
		this.dataManager.register(STINGING, Boolean.valueOf(false));
		this.dataManager.register(POLLEN, Integer.valueOf(0));
		this.dataManager.register(HOME, BlockPos.ORIGIN);
	}

	@Override
	protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityBee.AIMeleeAttack());
		this.tasks.addTask(2, new EntityBee.AIEnterBeehive(this));
		this.tasks.addTask(3, new EntityBee.AIPollenizeNearbyFlowers(this, 10));
		this.tasks.addTask(4, new EntityAIWanderAvoidWaterFlying(this, 0.8D));
		this.targetTasks.addTask(1, new EntityBee.AIHurtByTarget());
		this.targetTasks.addTask(2, new EntityBee.AIAttackPlayer());
	}
	
	@Override
	protected PathNavigate createNavigator(World worldIn) {
		PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
//		pathnavigateflying.setCanFloat(true);
        return pathnavigateflying;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.3D);
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) {
		// DONT CALL SUPER IN ORDER TO NOT TRIGGER FALL DAMAGE
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if (!this.onGround) {
	        if (this.motionY < 0.0D) this.motionY *= 0.6D;
	        if (this.motionY > 0.1D) this.motionY = 0.1D;
	    }
	}

	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
		super.damageEntity(damageSrc, damageAmount);
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

	public void setPollen(int amount) {
		this.dataManager.set(POLLEN, Integer.valueOf(amount));
	}

	public int getPollen() { return this.dataManager.get(POLLEN).intValue(); }
	
	public boolean isMaxPollen() { return getPollen() >= 2; } // TODO make it more later: 5 flowers at least

	public void setHome(BlockPos pos) {
		this.dataManager.set(HOME, pos);
	}

	public BlockPos getHome() {
		return this.dataManager.get(HOME);
	}

	public boolean wantsToEnterHive() {
		return !world.isDaytime() || isMaxPollen();
	}
	
	@Override
	protected void updateAITasks() {
		super.updateAITasks();

		if (this.getAttackTarget() == null && isAngry()) {
	        setAngry(false);
	    }
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItemMainhand();
		Main.getLogger().info("Right-click");
		if (stack.isEmpty()) {
			if (!world.isRemote) setPollen(2); // max pollen
			return true;
		}
		return super.processInteract(player, hand);
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		float damage = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();

		if (entityIn instanceof EntityLivingBase) {
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potion.getPotionFromResourceLocation("minecraft:poison"), 20 * 5, 1));
		}
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
				this.alertOthers(); // TODO : implement alert others for all bees not only children
				this.resetTask();
			}
		}
		
		@Override
	    public boolean shouldContinueExecuting() {
	        return EntityBee.this.isAngry() && super.shouldContinueExecuting();
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
		
		@Override
	    public boolean shouldContinueExecuting() {
	        return EntityBee.this.isAngry() && EntityBee.this.getAttackTarget() != null;
	    }

		protected double getTargetDistance() { return super.getTargetDistance() * 0.5D; }
	}
	
	class AIMeleeAttack extends EntityAIAttackMelee {
		public AIMeleeAttack() {
			super(EntityBee.this, 5.0D, true);
		}

		protected void checkAndPerformAttack(EntityLivingBase target, double p_190102_2_) {
			double d0 = this.getAttackReachSqr(target);

			if (p_190102_2_ <= d0 && this.attackTick <= 0) {
				this.attackTick = 20;
				this.attacker.attackEntityAsMob(target);
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

	public class AIPollenizeNearbyFlowers extends EntityAIBase {
		private final EntityBee bee;
		private final int range; // 5 should be enough, otherwise wander randomly
		private int updateTicks = 100; // 5 seconds
		private BlockPos targetPos;
		private int pollenizingTimer = 0;
		private static final int POLLENIZE_TICKS = 20; // time spent on flower
		
		public AIPollenizeNearbyFlowers(EntityBee bee, int searchedRange) {
			this.bee = bee;
			this.range = searchedRange;
			this.setMutexBits(AiMutexBits.MOVE | AiMutexBits.LOOK);
		}

		@Override
		public boolean shouldExecute() {
			return !bee.isMaxPollen() && world.isDaytime() && !bee.isAngry() && bee.getNavigator().noPath();
		}
		
		@Override
		public void updateTask() {
			if (targetPos == null) {
		        if (updateTicks <= 0) {
		            searchForFlowers();
		            updateTicks = 100;
		        } else {
		            updateTicks--;
		        }
		        return;
		    }
			
			double distSq = this.bee.getDistanceSq(
			        targetPos.getX() + 0.5,
			        targetPos.getY() + 0.5,
			        targetPos.getZ() + 0.5
			    );
			
			if (distSq > 4.0D) {
		        if (bee.getNavigator().noPath()) {
		            moveToTargetPos();
		        }
		        pollenizingTimer = 0;
		        return;
		    }
			
			bee.getNavigator().clearPath();
		    pollenizingTimer++;

		    if (pollenizingTimer >= POLLENIZE_TICKS) {
		        IBlockState state = world.getBlockState(targetPos);
		        if (state.getBlock() == ModBlocks.ROSE_FLOWER) {
		            BlockRose flower = (BlockRose) state.getBlock();
		            if (!flower.getPollenized(world, targetPos)) {
		                flower.setPollenized(world, targetPos, true);
		                bee.setPollen(bee.getPollen() + 1);
		            }
		        }
		        pollenizingTimer = 0;
		        targetPos = null;
		    }
		}
		
		private void moveToTargetPos() {
			bee.getNavigator().tryMoveToXYZ(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 1.0D);
		}

		@Override
		public void resetTask() {
			updateTicks = 100;
			targetPos = null;
		}

		private void searchForFlowers() {
			AxisAlignedBB searchedBox = bee.getEntityBoundingBox().grow(range);
			BlockPos min = new BlockPos(searchedBox.minX, searchedBox.minY, searchedBox.minZ);
			BlockPos max = new BlockPos(searchedBox.maxX, searchedBox.maxY, searchedBox.maxZ);
			for (BlockPos pos : BlockPos.getAllInBoxMutable(min, max)) {
				IBlockState state = world.getBlockState(pos);
				if (state.getBlock() == ModBlocks.ROSE_FLOWER) {
					BlockRose flower = (BlockRose) state.getBlock();
					boolean pollenized = flower.getPollenized(world, pos);
					if (!pollenized) {
						targetPos = pos.toImmutable();
						break;
					}
				}
			}
		}
	}

	// FIXME : don't call tryMoveTo every tick, this should be a one-shot task or maybe a 40 ticks cooldown task
	class AIEnterBeehive extends EntityAIBase {
	    private final EntityBee bee;

	    public AIEnterBeehive(EntityBee bee) {
	        this.bee = bee;
	        this.setMutexBits(AiMutexBits.MOVE | AiMutexBits.LOOK);
	    }

	    @Override
	    public boolean shouldExecute() {
	        return bee.wantsToEnterHive()
	            && !bee.getHome().equals(BlockPos.ORIGIN);
	    }
	    
	    @Override
	    public boolean shouldContinueExecuting() {
	        return !bee.getHome().equals(BlockPos.ORIGIN);
	    }

	    @Override
	    public void updateTask() {
	        BlockPos home = bee.getHome();
	        IBlockState state = bee.world.getBlockState(home);
	        EnumFacing facing = state.getValue(BeehiveBlock.FACING);
	        BlockPos target = home.offset(facing);
	        bee.getNavigator().tryMoveToXYZ(
	            target.getX() + 0.5,
	            target.getY() + 0.5,
	            target.getZ() + 0.5,
	            1.0D
	        );

	        double dist = bee.getDistanceSq(
	            home.getX() + 0.5,
	            home.getY() + 0.5,
	            home.getZ() + 0.5
	        );

	        if (dist <= 2.0D) {
	            TileEntity te = bee.world.getTileEntity(home);
	            if (te instanceof TileEntityBeehive) {
	                TileEntityBeehive hive = (TileEntityBeehive) te;
	                hive.setBeeCount(hive.getBeeCount() + 1);
	                hive.markDirty();
	            }
	            bee.setDead();
	        }
	    }
	}
}