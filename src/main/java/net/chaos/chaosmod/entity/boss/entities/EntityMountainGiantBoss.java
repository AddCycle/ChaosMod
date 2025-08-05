package net.chaos.chaosmod.entity.boss.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.chaos.chaosmod.entity.LittleGiantEntity;
import net.chaos.chaosmod.entity.ai.EntityAICustomRangedAttack;
import net.chaos.chaosmod.entity.projectile.EntityRock;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMountainGiantBoss extends EntityMob implements IRangedAttackMob {
    public final BossInfoServer bossInfo;
    public boolean attacking = false;
    private int attackTimer = 0;
    private int minionsMax = 10;
    private List<LittleGiantEntity> entity_pool = new ArrayList<LittleGiantEntity>();
    private static final AxisAlignedBB ENTITY_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 20.0D, 20.0D, 20.0D);
    private static final DataParameter<Boolean> SWINGING_ARMS = EntityDataManager.createKey(EntityMountainGiantBoss.class, DataSerializers.BOOLEAN);

	public EntityMountainGiantBoss(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
		this.noClip = false;
		this.setNoGravity(false);
		bossInfo = new BossInfoServer(getDisplayName(), Color.BLUE, Overlay.PROGRESS);
		this.bossInfo.setName(this.getDisplayName());
		this.setSize(1.5f, 3);
	}
	
	@Override
	protected void applyEntityAttributes() {
	    super.applyEntityAttributes();
	    int difficulty = world.getDifficulty().getDifficultyId();
	    this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(difficulty * 200.0D); // easy, normal | hard | >
	    this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(difficulty * 0.25D);
	    this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(difficulty * 2.0D);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(SWINGING_ARMS, false);
	}
	
	@Override
    protected void initEntityAI()
    {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAICustomRangedAttack(this, 1.0D, 20 * 2, 15.0F));
        this.tasks.addTask(1, new EntityAIAttackMelee(this, 0.75D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        // this.tasks.addTask(8, new EntityAILookIdle(this));
        this.applyEntityAI();
    }

    protected void applyEntityAI()
    {
        this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityPigZombie.class}));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityPlayer.class}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntitySheep.class, false));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
    }

    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        EntityRock rock = new EntityRock(this.world, this);
        double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - this.posX;
        double d2 = d0 - rock.posY;
        double d3 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        rock.shoot(d1, d2 + (double)f, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(rock);
    }

    public void startAttack() {
    	this.attacking = true;
    	this.attackTimer = 20;
    	this.setSwingingArms(true);
    	if (!world.isRemote) {
    		if (this.getMaxHealth() / 2 >= this.getHealth()) {
    			if (!entity_pool.isEmpty()) {
    				Iterator<LittleGiantEntity> iter = entity_pool.iterator();
    				while (iter.hasNext()) {
    				    LittleGiantEntity e = iter.next();
    				    if (e.isDead) {
    				        iter.remove();
    				    }
    				}
    			}

    			if (entity_pool.size() >= this.minionsMax) return;

    			LittleGiantEntity little = new LittleGiantEntity(world);
    			little.setPosition(this.posX + 0.5, this.posY, this.posZ + 0.5);
    			entity_pool.add(little);
    			world.spawnEntity(little);
    		}
    	}
    }

    public boolean isAttacking() {
        return attacking;
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
		
		if (attacking) {
		    attackTimer--;
		    if (attackTimer <= 0) {
		        attacking = false;
		        setSwingingArms(false);
		    }
		}
	}
	
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
	protected void onDeathUpdate() {
        ++this.deathTime;

        if (!world.isRemote) {
        	this.motionY = 0.02D; // Constant upward motion
        	this.posY += this.motionY;
        }
        
        if (this.deathTime == 1) {
        	this.noClip = true;
        	this.setNoGravity(true);
        }

        if (this.deathTime >= 200)
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


            List<EntityPlayer> nearby_players = null;
            int count = 0;
            if (!world.isRemote) {
            	int radius = 50; // FIXME: boss chamber radius
            	 nearby_players = world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(radius));
            	 count = nearby_players.size();
            }

            if (!world.isRemote) {
            	for (EntityPlayerMP pl : world.getMinecraftServer().getPlayerList().getPlayers()) {
            		pl.sendMessage(new TextComponentTranslation("entity.mountain_giant_boss.death_message"));
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
            
            if (!world.isRemote) {
            	world.spawnEntity(new EntityItem(world, this.posX, this.posY, this.posZ, new ItemStack(ModItems.GIANT_HEART, count == 0 ? 1 : count)));
            	world.spawnEntity(new EntityItem(world, this.posX, this.posY, this.posZ, new ItemStack(ModBlocks.BRAVE_TROPHY, count == 0 ? 1 : count)));
            }
        }
        else
        {
        	float progress = 1.0F - (float) this.deathTime / 200.0F;
            this.bossInfo.setPercent(Math.max(progress, 0.0F));
            this.bossInfo.setColor(BossInfo.Color.GREEN);
            this.bossInfo.setName(new TextComponentTranslation("boss.mountain_giant.death")); // FIXME : add localization
        }
	}
	
	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}
	
	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public AxisAlignedBB getEntityBoundingBox() {
		return super.getEntityBoundingBox();
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		return ENTITY_AABB;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		return getEntityBoundingBox();
	}
	
	@Override
	public void setSwingingArms(boolean swingingArms) {
	    this.dataManager.set(SWINGING_ARMS, swingingArms);
	}

	public boolean isSwingingArms() {
	    return this.dataManager.get(SWINGING_ARMS);
	}
}
