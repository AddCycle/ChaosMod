package net.chaos.chaosmod.entity.animal;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * TODO : implement tame (with honey or fish) (extends EntityTameable implements IEntityOwnable)
 * TODO : implement sit & follow_player (ai)
 * TODO : implement attack (ai)
 * TODO : implement wear an armor (layer)
 */
public class EntityBear extends EntityTameable {
    private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.<Boolean>createKey(EntityBear.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> VARIANT = EntityDataManager.<Integer>createKey(EntityBear.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> SIT_POSE = EntityDataManager
    	    .<Integer>createKey(EntityBear.class, DataSerializers.VARINT);
    private float clientSideStandAnimation0;
    private float clientSideStandAnimation;
    private int warningSoundTicks;
	private EntityAITempt aiTempt;

    public EntityBear(World world)
    {
        super(world);
        this.setSize(1.3F, 1.4F);
        this.setVariant(EnumVariant.get(rand.nextInt(EnumVariant.values().length)));
    }

    public EntityBear(World world, int fatherVariant)
    {
        super(world);
        this.setSize(1.3F, 1.4F);
        this.setVariant(EnumVariant.get(fatherVariant));
    }

    public EntityAgeable createChild(EntityAgeable ageable)
    {
    	boolean mutation = rand.nextInt(20) == 0;
    	int variant = mutation ? EnumVariant.getOtherVariant(this.getVariant()) : this.getVariant();
        return new EntityBear(this.world, variant);
    }

    /**
     * Checks if the parameter is an item which this animal can be fed to breed it (wheat, carrots or seeds depending on
     * the animal type)
     */
    public boolean isBreedingItem(ItemStack stack)
    {
//        return (stack.getItem() instanceof CustomFishFood)
//        	|| (stack.getItem() instanceof ItemFishFood);
    	return false;
    }

    protected void initEntityAI()
    {
        super.initEntityAI();
        this.aiSit = new EntityAISit(this);
        this.aiTempt = new EntityAITempt(this, 1.0D, Items.FISH, false);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityBear.AIMeleeAttack());
        this.tasks.addTask(1, new EntityBear.AIPanic());
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, this.aiTempt);
        this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityBear.AIHurtByTarget());
        this.targetTasks.addTask(2, new EntityBear.AIAttackPlayer());
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    protected SoundEvent getAmbientSound()
    {
        return this.isChild() ? SoundEvents.ENTITY_POLAR_BEAR_BABY_AMBIENT : SoundEvents.ENTITY_POLAR_BEAR_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
    	return getTotalArmorValue() > 0 ? SoundEvents.ENTITY_HORSE_ARMOR : SoundEvents.ENTITY_POLAR_BEAR_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F);
    }

    protected void playWarningSound()
    {
        if (this.warningSoundTicks <= 0)
        {
            this.playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1.0F, 1.0F);
            this.warningSoundTicks = 40;
        }
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_POLAR_BEAR;
    }

    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(IS_STANDING, Boolean.valueOf(false));
        this.dataManager.register(VARIANT, rand.nextInt(EnumVariant.values().length));
        this.dataManager.register(SIT_POSE, 0);
    }
    
    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);

        if (this.isTamed())
        {
            if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack))
            {
            	setSittingWithAnimation(!this.isSitting());
            }
        }
        else if ((this.aiTempt == null || this.aiTempt.isRunning()) && itemstack.getItem() == Items.FISH && player.getDistanceSq(this) < 9.0D)
        {
            if (!player.capabilities.isCreativeMode)
            {
                itemstack.shrink(1);
            }

            if (!this.world.isRemote)
            {
                if (this.rand.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player))
                {
                    this.setTamedBy(player);
//                    this.setTameSkin(1 + this.world.rand.nextInt(3));
                    this.playTameEffect(true);
                    setSittingWithAnimation(true);
                    this.world.setEntityState(this, (byte)7);
                }
                else
                {
                    this.playTameEffect(false);
                    this.world.setEntityState(this, (byte)6);
                }
            }

            return true;
        }

    	return super.processInteract(player, hand);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (this.world.isRemote)
        {
            this.clientSideStandAnimation0 = this.clientSideStandAnimation;

            if (this.isStanding())
            {
                this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
            }
            else
            {
                this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
            }
        }

        if (this.warningSoundTicks > 0)
        {
            --this.warningSoundTicks;
        }
    }
    
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            if (this.aiSit != null)
            {
                this.aiSit.setSitting(false);
            }

            return super.attackEntityFrom(source, amount);
        }
    }

    public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public boolean isStanding()
    {
        return ((Boolean)this.dataManager.get(IS_STANDING)).booleanValue();
    }

    public void setStanding(boolean standing)
    {
        this.dataManager.set(IS_STANDING, Boolean.valueOf(standing));
    }

    public void setVariant(EnumVariant variant)
    {
        this.dataManager.set(VARIANT, variant.id);
    }

    public int getVariant()
    {
        return this.dataManager.get(VARIANT);
    }

    @SideOnly(Side.CLIENT)
    public float getStandingAnimationScale(float p_189795_1_)
    {
        return (this.clientSideStandAnimation0 + (this.clientSideStandAnimation - this.clientSideStandAnimation0) * p_189795_1_) / 6.0F;
    }
    
    public int getSitPose() {
    	return this.dataManager.get(SIT_POSE);
    }

    private void setSittingWithAnimation(boolean b) {
    	this.dataManager.set(SIT_POSE, rand.nextInt(2)); // 0 or 1
    	this.aiSit.setSitting(b);
    }

    protected float getWaterSlowDown()
    {
        return 0.98F;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        if (livingdata instanceof EntityBear.GroupData)
        {
            if (((EntityBear.GroupData)livingdata).madeParent)
            {
                this.setGrowingAge(-24000);
            }
        }
        else
        {
            EntityBear.GroupData entitypolarbear$groupdata = new EntityBear.GroupData();
            entitypolarbear$groupdata.madeParent = true;
            livingdata = entitypolarbear$groupdata;
        }

        return livingdata;
    }

    class AIAttackPlayer extends EntityAINearestAttackableTarget<EntityPlayer>
    {
        public AIAttackPlayer()
        {
            super(EntityBear.this, EntityPlayer.class, 20, true, true, null);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            if (EntityBear.this.isChild())
            {
                return false;
            }
            else
            {
                if (super.shouldExecute())
                {
                    for (EntityBear entitypolarbear : EntityBear.this.world.getEntitiesWithinAABB(EntityBear.class, EntityBear.this.getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D)))
                    {
                        if (entitypolarbear.isChild())
                        {
                            return true;
                        }
                    }
                }

                EntityBear.this.setAttackTarget((EntityLivingBase)null);
                return false;
            }
        }

        protected double getTargetDistance()
        {
            return super.getTargetDistance() * 0.5D;
        }
    }

    class AIHurtByTarget extends EntityAIHurtByTarget
    {
        public AIHurtByTarget()
        {
            super(EntityBear.this, false);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting()
        {
            super.startExecuting();

            if (EntityBear.this.isChild())
            {
                this.alertOthers();
                this.resetTask();
            }
        }

        protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn)
        {
            if (creatureIn instanceof EntityBear && !creatureIn.isChild())
            {
                super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
            }
        }
    }

    class AIMeleeAttack extends EntityAIAttackMelee
    {
        public AIMeleeAttack()
        {
            super(EntityBear.this, 1.25D, true);
        }

        protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_)
        {
            double d0 = this.getAttackReachSqr(p_190102_1_);

            if (p_190102_2_ <= d0 && this.attackTick <= 0)
            {
                this.attackTick = 20;
                this.attacker.attackEntityAsMob(p_190102_1_);
                EntityBear.this.setStanding(false);
            }
            else if (p_190102_2_ <= d0 * 2.0D)
            {
                if (this.attackTick <= 0)
                {
                    EntityBear.this.setStanding(false);
                    this.attackTick = 20;
                }

                if (this.attackTick <= 10)
                {
                    EntityBear.this.setStanding(true);
                    EntityBear.this.playWarningSound();
                }
            }
            else
            {
                this.attackTick = 20;
                EntityBear.this.setStanding(false);
            }
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
            EntityBear.this.setStanding(false);
            super.resetTask();
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget)
        {
            return (double)(4.0F + attackTarget.width);
        }
    }

    class AIPanic extends EntityAIPanic
    {
        public AIPanic()
        {
            super(EntityBear.this, 2.0D);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return !EntityBear.this.isChild() && !EntityBear.this.isBurning() ? false : super.shouldExecute();
        }
    }

    static class GroupData implements IEntityLivingData
        {
            public boolean madeParent;

            private GroupData()
            {
            }
        }
    
    public static enum EnumVariant {
    	BROWN(0),
    	ORANGE(1);
    	
    	public int id;
    	
    	EnumVariant(int id) {
    		this.id = id;
		}
    	
    	// TODO : if more variants are added, needs to change otherwise might cause issues
    	public static int getOtherVariant(int variant) {
    		return 1 - variant;
    	}
    	
    	public static EnumVariant get(int variant) {
    		if (variant == 0) return BROWN;
    		return BROWN;
    	}
    }
}