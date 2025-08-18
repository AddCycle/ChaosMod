package net.chaos.chaosmod.entity.boss.entities;

import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModDamageSources;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class EntityEyeCrystalBoss extends EntityEnderCrystal {
	@Nullable
	private EntityLivingBase laserTarget;
	public int laserTicks;
	private int health;
	private int max_health;
    public BossInfoServer bossInfo;
    public static final DataParameter<Integer> LASER_TARGET_ID =
    	EntityDataManager.createKey(EntityEyeCrystalBoss.class, DataSerializers.VARINT);
    public static final DataParameter<Float> CLIENT_HEALTH = EntityDataManager.createKey(EntityEyeCrystalBoss.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> CLIENT_MAX_HEALTH = EntityDataManager.createKey(EntityEyeCrystalBoss.class, DataSerializers.FLOAT);
    
    public EntityEyeCrystalBoss(World worldIn) {
    	super(worldIn);
    	this.setSize(1.5f, 1.5f);
    	this.setup(100);
    }

    public EntityEyeCrystalBoss(World worldIn, double x, double y, double z) {
    	this(worldIn, x, y, z, 100);
    	this.setSize(1.5f, 1.5f);
    }

    public EntityEyeCrystalBoss(World worldIn, double x, double y, double z, int health) {
    	super(worldIn, x, y, z);
    	this.setSize(1.5f, 1.5f);
    	this.setup(health);
    }

	public void setup(int health) {
	    this.health = health;
	    this.max_health = health;

	    // sync data
	    this.dataManager.set(CLIENT_HEALTH, (float) this.health);
	    this.dataManager.set(CLIENT_MAX_HEALTH, (float) this.max_health);

	    // boss bar setup
	    this.bossInfo = new BossInfoServer(this.getDisplayName(), Color.PURPLE, Overlay.PROGRESS);
	    Main.getLogger().info("Setting up boss info server");
	    this.bossInfo.setPercent(1f);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		Main.getLogger().info("Setting data parameter for eye crystal boss :");
		this.dataManager.register(LASER_TARGET_ID, -1); // -1 means no target
		this.dataManager.register(CLIENT_HEALTH, this.getHealth());
		this.dataManager.register(CLIENT_MAX_HEALTH, this.getMaxHealth());
	}
	
	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}
	
	@Override
	public boolean shouldShowBottom() {
		return false;
	}
	
	@Override
	public BlockPos getBeamTarget() {
		return super.getBeamTarget();
	}
	
	@Override
	public void setBeamTarget(BlockPos beamTarget) {
		super.setBeamTarget(beamTarget);
	}
	
	@Override
	public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
		return super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		health -= 0.5; // FIXME : balance this shit
		if (this.isEntityAlive()) {
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
		if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (source.getTrueSource() instanceof EntityDragon)
        {
            return false;
        }
        else
        {
            if (!this.isDead && !this.world.isRemote)
            {
                if (!this.world.isRemote)
                {
                    if (!source.isExplosion())
                    {
                        this.world.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 3.0F, false);
                    }

                    this.onCrystalDestroyed(source);
                    if (this.health <= 0) {
                    	this.setDead();
                    	BlockPos pos = new BlockPos(401, 81, 401);
                    	world.setBlockState(pos, ModBlocks.OXONIUM_CHEST.getDefaultState(), 2);
                    	world.setBlockState(pos.down(4), ModBlocks.BEAM_BLOCK.getDefaultState(), 2);
                    	TileEntityOxoniumChest te = (TileEntityOxoniumChest) world.getTileEntity(pos);
                    	te.setInventorySlotContents(13, new ItemStack(ModBlocks.EYE_TROPHY, 3));
                    	te.setInventorySlotContents(21, new ItemStack(ModItems.ENDERITE_THUNDER, 3));
                    	te.setInventorySlotContents(22, new ItemStack(ModItems.CHAOS_HEART));
                    }
                }
            }
            return true;
        }
	}
	
	private void onCrystalDestroyed(DamageSource source)
    {
		// FIXME : balance this shit
        if (this.world.provider instanceof WorldProviderEnd)
        {
            this.world.setRainStrength(1.0f);
        }
    }

	@Override
	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		if (bossInfo != null) this.bossInfo.addPlayer(player);
	}
	
	@Override
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		if (bossInfo != null) this.bossInfo.removePlayer(player);
	}
	
	public float getHealth() {
		return Math.max(0, this.health);
	}

	public float getMaxHealth() {
		return this.max_health;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (bossInfo == null) return;

		if (this.isEntityAlive()) { 
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
			if (!this.world.isRemote) {
			    this.dataManager.set(CLIENT_HEALTH, this.getHealth());
			    this.dataManager.set(CLIENT_MAX_HEALTH, this.getMaxHealth());
			}
        }
		
		if (!world.isRemote) {
	        if (laserTarget != null && laserTarget.isEntityAlive()) {
	        	if (laserTicks == 0 && this.rand.nextBoolean()) {
	                world.playSound(null, laserTarget.posX, laserTarget.posY, laserTarget.posZ,
	                	SoundEvents.ENTITY_GHAST_WARN, SoundCategory.HOSTILE, 0.5F, 1.0F);
	        	}
	            laserTicks++;

	            // looking at target
	            double dx = laserTarget.posX - posX;
	            double dy = (laserTarget.posY + laserTarget.getEyeHeight()) - (posY + height * 0.5);
	            double dz = laserTarget.posZ - posZ;
	            rotationYaw = (float) (MathHelper.atan2(dz, dx) * (180D / Math.PI)) - 90F;
	            rotationPitch = (float) (-(MathHelper.atan2(dy, MathHelper.sqrt(dx * dx + dz * dz)) * (180D / Math.PI)));

	            if (laserTicks >= 40) {
	            	// Damage the target
	                laserTarget.attackEntityFrom(ModDamageSources.LASER_DAMAGE, 6.0F);
	                world.playSound(null, laserTarget.posX, laserTarget.posY, laserTarget.posZ,
	                	    SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.HOSTILE, 0.5F, 0.3F);
	                laserTicks = 0;
	                laserTarget = null;
	            }
	        } else {
	            laserTarget = findNearestTarget();
	            laserTicks = 0;
	            
	        }
	        if (laserTarget != null && laserTicks > 20) {
	        	this.dataManager.set(LASER_TARGET_ID, laserTarget.getEntityId());
	        } else {
	        	this.dataManager.set(LASER_TARGET_ID, -1);
	        }
	    }
	}
	
	// ver 1
	/*private EntityLivingBase findNearestTarget() {
		double horizontal = 256;
		double vertical = 256; // or bigger if your turret is high up

		AxisAlignedBB box = new AxisAlignedBB(
		    posX - horizontal, posY - vertical, posZ - horizontal,
		    posX + horizontal, posY + vertical, posZ + horizontal
		);
	    List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, box);

	    EntityLivingBase closest = null;
	    double closestDistSq = Double.MAX_VALUE;

	    for (EntityLivingBase target : list) {
	        if (target.isEntityAlive()) {
	        	double distSq = getDistanceSq(target);

	            if (distSq < closestDistSq) {
	                closest = target;
	                closestDistSq = distSq;
	            }
	        }
	    }

	    return closest;
	}*/
	
	private EntityLivingBase findNearestTarget() {
	    double horizontal = 32;
	    double vertical = 32;

	    AxisAlignedBB box = new AxisAlignedBB(
	        posX - horizontal, posY - vertical, posZ - horizontal,
	        posX + horizontal, posY + vertical, posZ + horizontal
	    );

	    List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, box);

	    EntityPlayer closest = null;
	    double closestDistSq = Double.MAX_VALUE;

	    for (EntityPlayer player : list) {
	        if (player.isEntityAlive() && !player.isSpectator()) {
	            double distSq = getDistanceSq(player);
	            if (distSq < closestDistSq) {
	                closest = player;
	                closestDistSq = distSq;
	            }
	        }
	    }

	    return closest;
	}

	public Entity getLaserTarget() {
	    return laserTarget;
	}

	public void setLaserTarget(EntityLivingBase target) {
	    this.laserTarget = (EntityLivingBase) target;
	}
	
	public int getLaserTicks() {
		return laserTicks;
	}

	@Override
	public float getEyeHeight() {
	    return 0.75f;
	}	

}
