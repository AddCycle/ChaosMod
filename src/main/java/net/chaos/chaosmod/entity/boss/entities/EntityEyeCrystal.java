package net.chaos.chaosmod.entity.boss.entities;

import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModDamageSources;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySpectralArrow;
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
import net.minecraft.world.end.DragonFightManager;

public class EntityEyeCrystal extends EntityEnderCrystal {
	@Nullable
	private EntityLivingBase laserTarget;
	public int laserTicks;
	private int health;
	private int max_health;
    public BossInfoServer bossInfo;
    public boolean isBoss;
    public static final DataParameter<Integer> LASER_TARGET_ID =
    	EntityDataManager.createKey(EntityEyeCrystal.class, DataSerializers.VARINT);
    
    public EntityEyeCrystal(World worldIn) {
    	super(worldIn);
    	if (this.isBoss) {
    		bossInfo = new BossInfoServer(this.getDisplayName(), Color.PURPLE, Overlay.PROGRESS);
    		this.bossInfo.setName(this.getDisplayName());
    	}
		this.setSize(1.5f, 1.5f);
		// this is for vanilla /summon <>
		if (this.health == 0 && this.max_health == 0) {
			this.health = 20;
			this.max_health = 20;
		}
		// this is for the minions dummy shield
		if (this.health == 1 && this.max_health == 1) {
			bossInfo = null;
			this.health = 20;
			this.max_health = 20;
		}
    }

	public EntityEyeCrystal(World worldIn, int health, boolean isBoss) {
		this(worldIn);
		this.health = health;
		this.max_health = health;
		this.isBoss = isBoss;
	}

	public EntityEyeCrystal(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
		if (this.isBoss) {
			System.out.println("Setting boss info server");
			bossInfo = new BossInfoServer(this.getDisplayName(), Color.PURPLE, Overlay.PROGRESS);
			this.bossInfo.setName(this.getDisplayName());
		}
		this.setSize(1.5f, 1.5f);
	}

	public EntityEyeCrystal(World worldIn, double x, double y, double z, int health, boolean isBoss) {
		super(worldIn, x, y, z);
		this.setSize(1.5f, 1.5f);

		this.health = health;
		System.out.println("setting health : " + this.health);
		this.max_health = health;
		System.out.println("setting max health : " + this.max_health);
		this.isBoss = isBoss;
		System.out.println("setting boss status : " + this.max_health);

		if (isBoss) {
			System.out.println("Setting boss info server");
			bossInfo = new BossInfoServer(this.getDisplayName(), Color.PURPLE, Overlay.PROGRESS);
			this.bossInfo.setName(this.getDisplayName());
		}
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(LASER_TARGET_ID, -1); // -1 means no target
	}
	
	@Override
	public boolean isImmuneToExplosions() {
		return true;
	}
	
	@Override
	public boolean shouldShowBottom() {
		return isBoss; // only minions
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
		if (this.isBoss && this.isEntityAlive()) {
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
                        this.world.createExplosion((Entity)null, this.posX, this.posY, this.posZ, 10.0F, false);
                    }

                    this.onCrystalDestroyed(source);
                    System.out.println("Health : " + this.health);
                    if (this.health <= 0) {
                    	this.setDead();
                    	// TODO : refactor (eye loots part)
                    	if (this.isBoss) {
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
            }
            return true;
        }
	}
	
	private void onCrystalDestroyed(DamageSource source)
    {
        if (this.world.provider instanceof WorldProviderEnd)
        {
            WorldProviderEnd worldproviderend = (WorldProviderEnd)this.world.provider;
            DragonFightManager dragonfightmanager = worldproviderend.getDragonFightManager();

            if (dragonfightmanager != null)
            {
                dragonfightmanager.onCrystalDestroyed(this, source);
            }
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
		return health;
	}

	public float getMaxHealth() {
		return max_health;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (bossInfo == null) return;

		if (this.isEntityAlive()) { 
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
		
		if (!world.isRemote) {
	        if (laserTarget != null && laserTarget.isEntityAlive()) {
	            laserTicks++;

	            // looking at target
	            double dx = laserTarget.posX - posX;
	            double dy = laserTarget.posY + laserTarget.getEyeHeight() - (posY + getEyeHeight());
	            double dz = laserTarget.posZ - posZ;
	            rotationYaw = (float) (MathHelper.atan2(dz, dx) * (180D / Math.PI)) - 90F;

	            if (laserTicks >= 40) {
	                // Damage the target
	            	// EntitySpectralArrow arrow = new EntitySpectralArrow(world, laserTarget.posX, laserTarget.posY, laserTarget.posZ);
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
	
	private EntityLivingBase findNearestTarget() {
		// a little box
	    // AxisAlignedBB box = new AxisAlignedBB(posX - 16, posY - 8, posZ - 16, posX + 16, posY + 8, posZ + 16);
		// big box 16x16x16
		int height = 16;
		int width = 16; // was too much
	    AxisAlignedBB box = new AxisAlignedBB(posX - width, posY - height, posZ - width, posX + width, posY + height, posZ + width);
	    List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, box);

	    EntityLivingBase closest = null;
	    double closestDistSq = Double.MAX_VALUE;

	    for (EntityLivingBase target : list) {
	        if (target.isEntityAlive()) {
	            double distSq = getDistanceSq(target);
	            if (distSq < closestDistSq && ((this instanceof Entity) && getDistance(target) <= 1000)) {
	                closest = target;
	                closestDistSq = distSq;
	            } else {
				}
	        }
	    }

	    // System.out.println("Closest = " + closest);
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

	public boolean isBoss() {
		return isBoss;
	}

}
