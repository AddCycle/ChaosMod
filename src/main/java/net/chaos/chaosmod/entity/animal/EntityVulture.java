package net.chaos.chaosmod.entity.animal;

import java.util.Random;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFollow;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class EntityVulture extends EntityFlying implements net.minecraft.entity.passive.EntityFlying {

	public EntityVulture(World worldIn) {
		super(worldIn);
        this.moveHelper = new EntityFlyHelper(this);
        this.setSize(1f, 0.5f);
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
		int surface = this.world.getHeight((int) this.posX, (int) this.posZ);
	    double spawnY = surface + 20 + this.world.rand.nextInt(31); // 20-50 blocks above ground
	    this.setPosition(this.posX, spawnY, this.posZ);
		return super.onInitialSpawn(difficulty, livingdata);
	}
	
	@Override
	protected PathNavigate createNavigator(World worldIn) {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(false);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
	}

	@Override
	protected void initEntityAI() {
//        this.tasks.addTask(0, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
//        this.tasks.addTask(2, new EntityAIWanderAvoidWaterFlying(this, 1.0f));
//        this.tasks.addTask(2, this.aiSit);
//        this.tasks.addTask(2, new EntityAIFollowOwnerFlying(this, 1.0D, 5.0F, 1.0F));
//        this.tasks.addTask(2, new EntityAIWanderAvoidWaterFlying(this, 1.0D));
//        this.tasks.addTask(3, new EntityAILandOnOwnersShoulder(this));
        this.tasks.addTask(2, new AIRandomFly(this));
        this.tasks.addTask(3, new EntityAIFollow(this, 1.0D, 3.0F, 7.0F)); // follow same species
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.8000000059604645D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.80000000298023224D);
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return super.getCanSpawnHere();
	}

    static class AIRandomFly extends EntityAIBase
        {
            private final EntityVulture parentEntity;

            public AIRandomFly(EntityVulture vulture)
            {
                this.parentEntity = vulture;
                this.setMutexBits(Constants.AiMutexBits.MOVE);
            }

            /**
             * Returns whether the EntityAIBase should begin execution.
             */
            public boolean shouldExecute()
            {
                EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

                if (!entitymovehelper.isUpdating())
                {
                    return true;
                }
                else
                {
                	double minDist = 1.0D;
                	double maxDist = 3600.0D;
                    double d0 = entitymovehelper.getX() - this.parentEntity.posX;
                    double d1 = entitymovehelper.getY() - this.parentEntity.posY;
                    double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
                    double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                    return d3 < minDist || d3 > maxDist;
                }
            }

            /**
             * Returns whether an in-progress EntityAIBase should continue executing
             */
            public boolean shouldContinueExecuting()
            {
                return false;
            }

            /**
             * Execute a one shot task or start executing a continuous task
             */
            public void startExecuting()
            {
                Random random = this.parentEntity.getRNG();
                float wanderRadius = 16.0F;
                float speed = 1.0f;
                double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * wanderRadius);
                double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * wanderRadius);
                double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * wanderRadius);
                this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, speed);
            }
        }
}