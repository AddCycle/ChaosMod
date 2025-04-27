package net.chaos.chaosmod.entity.boss.entities;

import javax.annotation.Nullable;

import net.chaos.chaosmod.entity.projectile.EntitySmallBlueFireball;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRevengeBlazeBoss extends EntityMob {
    private final BossInfoServer bossInfo;

    /** Random offset used in floating behaviour */
    private float heightOffset = 0.5F;
    /** ticks until heightOffset is randomized */
    private int heightOffsetUpdateTime;
    private static final DataParameter<Byte> ON_FIRE = EntityDataManager.<Byte>createKey(EntityBlaze.class, DataSerializers.BYTE);
	private static final DataParameter<Boolean> TRANSFORMED = EntityDataManager.createKey(EntityRevengeBlazeBoss.class, DataSerializers.BOOLEAN);

	public EntityRevengeBlazeBoss(World worldIn) {
		super(worldIn);
        this.setPathPriority(PathNodeType.WATER, -1.0F);
        this.setPathPriority(PathNodeType.LAVA, 8.0F);
        this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
        this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
        this.isImmuneToFire = true;
        this.experienceValue = 10;
        this.bossInfo = new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS);
        this.bossInfo.setName(this.getDisplayName());
	}

    public static void registerFixesBlaze(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityBlaze.class);
    }
	
	@Override
	protected void entityInit() {
	    super.entityInit();
        this.dataManager.register(ON_FIRE, Byte.valueOf((byte)0));
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

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_BLAZE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_BLAZE_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_BLAZE_DEATH;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }
    
    @Override
    protected boolean canDespawn() {
    	return false;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 1.0F;
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
	
	@Override
    public void onLivingUpdate() {

	    if (!world.isRemote) { // Server side
	        if (this.getHealth() < this.getMaxHealth() / 2 && !this.isTransformed()) {
	            this.dataManager.set(TRANSFORMED, true); // Force sync to client!
	        }
	    }
	    
	    if (this.isTransformed() && world.isRemote) { // Client side
	    	if (this.isDead) return;
	        for (int i = 0; i < 3; ++i) {
	            double offsetX = (this.rand.nextDouble() - 0.5D) * (double)this.width;
	            double offsetY = this.rand.nextDouble() * (double)this.height;
	            double offsetZ = (this.rand.nextDouble() - 0.5D) * (double)this.width;

	            this.world.spawnParticle(
	                EnumParticleTypes.FLAME,
	                this.posX + offsetX,
	                this.posY + offsetY,
	                this.posZ + offsetZ,
	                0.0D, 0.0D, 0.0D // super speed flames
	            );
	        }
	    }

        if (!this.onGround && this.motionY < 0.0D)
        {
            this.motionY *= 0.6D;
        }

        if (this.world.isRemote)
        {
            if (this.rand.nextInt(24) == 0 && !this.isSilent())
            {
                this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }

            for (int i = 0; i < 2; ++i)
            {
                // this.world.spawnParticle(EnumParticleTypes., this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
            }
        }
	    
	    super.onLivingUpdate();
    }

	@Override
    protected void updateAITasks()
    {
        if (this.isWet())
        {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }

        --this.heightOffsetUpdateTime;

        if (this.heightOffsetUpdateTime <= 0)
        {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
        }

        EntityLivingBase entitylivingbase = this.getAttackTarget();

        if (entitylivingbase != null && entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() > this.posY + (double)this.getEyeHeight() + (double)this.heightOffset)
        {
            this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            this.isAirBorne = true;
        }

        super.updateAITasks();
    }

    /**
     * Returns true if the entity is on fire. Used by render to add the fire effect on rendering.
     */
    public boolean isBurning()
    {
        return this.isCharged();
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_BLAZE;
    }

    public boolean isCharged()
    {
        return (((Byte)this.dataManager.get(ON_FIRE)).byteValue() & 1) != 0;
    }

    public void setOnFire(boolean onFire)
    {
        byte b0 = ((Byte)this.dataManager.get(ON_FIRE)).byteValue();

        if (onFire)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.dataManager.set(ON_FIRE, Byte.valueOf(b0));
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
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
            private final EntityRevengeBlazeBoss blaze;
            private int attackStep;
            private int attackTime;

            public AIFireballAttack(EntityRevengeBlazeBoss blazeIn)
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
                        	if (this.blaze.isTransformed()) {
                        		this.attackTime = 20;
                        		this.blaze.setOnFire(true);
                        	} else {
                        		this.attackTime = 60;
                        		this.blaze.setOnFire(true);
                        	}
                        }
                        else if (this.attackStep <= 4)
                        {
                            this.attackTime = 6;
                        }
                        else
                        {
                        	if (this.blaze.isTransformed()) {
                        		this.attackTime = 60;
                        	} else {
                        		this.attackTime = 100;
                        	}
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
                            	if (this.blaze.isTransformed()) {
                            		EntitySmallBlueFireball entitysmallfireball = new EntitySmallBlueFireball(this.blaze.world, this.blaze, d1 + this.blaze.getRNG().nextGaussian() * (double)f, d2, d3 + this.blaze.getRNG().nextGaussian() * (double)f);
                            		entitysmallfireball.posY = this.blaze.posY + (double)(this.blaze.height / 2.0F) + 0.5D;
                            		this.blaze.world.spawnEntity(entitysmallfireball);
                            	} else {
                            		EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.blaze.world, this.blaze, d1 + this.blaze.getRNG().nextGaussian() * (double)f, d2, d3 + this.blaze.getRNG().nextGaussian() * (double)f);
                            		entitysmallfireball.posY = this.blaze.posY + (double)(this.blaze.height / 2.0F) + 0.5D;
                            		this.blaze.world.spawnEntity(entitysmallfireball);
                            	}
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
    
    /*
     * This part concerns the boss health bar and other stuff related
     */
    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    }
    
    @Override
    protected void onDeathUpdate() {
        ++this.deathTime;

        if (this.deathTime >= 60)
        {
            if (!this.world.isRemote && (this.isPlayer() || this.recentlyHit > 0 && this.canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")))
            {
                int i = this.getExperiencePoints(this.attackingPlayer);
                i = net.minecraftforge.event.ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
                while (i > 0)
                {
                    int j = EntityXPOrb.getXPSplit(i);
                    i -= j;
                    this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
                }
            }

            this.setDead();

            for (int k = 0; k < 20; ++k)
            {
                double d2 = this.rand.nextGaussian() * 0.02D;
                double d0 = this.rand.nextGaussian() * 0.02D;
                double d1 = this.rand.nextGaussian() * 0.02D;
                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, d2, d0, d1);
            }
        } else {
        	float progress = (getMaxHealth() / 60) - this.deathTime;
        	this.bossInfo.setColor(BossInfo.Color.WHITE);
        	this.bossInfo.setPercent(progress / (getMaxHealth() / 60));
        	this.bossInfo.setName(new TextComponentString("Dying")); // FIXME add localization please
        }
    }
}
