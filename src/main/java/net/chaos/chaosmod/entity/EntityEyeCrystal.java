package net.chaos.chaosmod.entity;

import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.init.ModDamageSources;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class EntityEyeCrystal extends EntityEnderCrystal {
	@Nullable
	private EntityLivingBase laserTarget;
	public int laserTicks;
	private int health;
	private int max_health;
	public static final DataParameter<Integer> LASER_TARGET_ID =
			EntityDataManager.createKey(EntityEyeCrystal.class, DataSerializers.VARINT);
	public static final DataParameter<Float> CLIENT_HEALTH = EntityDataManager.createKey(EntityEyeCrystal.class, DataSerializers.FLOAT);
	public static final DataParameter<Float> CLIENT_MAX_HEALTH = EntityDataManager.createKey(EntityEyeCrystal.class, DataSerializers.FLOAT);

	public EntityEyeCrystal(World worldIn) {
		super(worldIn);
		this.setSize(1.5f, 1.5f);
		this.setup(10);
	}

	public EntityEyeCrystal(World worldIn, double x, double y, double z) {
		this(worldIn, x, y, z, 10); // health base /summon
		this.setSize(1.5f, 1.5f);
	}

	public EntityEyeCrystal(World worldIn, double x, double y, double z, int health) {
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
	}

	@Override
	protected void entityInit() {
		super.entityInit();
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
					if (this.health <= 0) {
						this.setDead();
						// TODO : maybe particle effect to know if the remaining minions to be killed
					}
				}
			}
			return true;
		}
	}

	private void onCrystalDestroyed(DamageSource source)
	{
		// FIXME : balance this shit or remove it
		if (this.world.provider instanceof WorldProviderEnd)
		{
			this.world.setRainStrength(1.0f);
		}
	}

	@Override
	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		// if (bossInfo != null) this.bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		// if (bossInfo != null) this.bossInfo.removePlayer(player);
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

		if (this.isEntityAlive()) { 
			if (!this.world.isRemote) {
				this.dataManager.set(CLIENT_HEALTH, this.getHealth());
				this.dataManager.set(CLIENT_MAX_HEALTH, this.getMaxHealth());
			}
		}

		if (!world.isRemote) {
			if (laserTarget != null && laserTarget.isEntityAlive() && laserTarget instanceof EntityPlayer) {
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

	            float damage = 5.0f;
	            if (this.getHealth() / this.getMaxHealth() > 0.5f) {
	            	damage = 3.0f;
	            } else {
	            	damage = 8.0f;
	            }
				if (laserTicks >= 20 * 5) { // every 5 seconds
					// Damage the target
					laserTarget.attackEntityFrom(ModDamageSources.LASER_DAMAGE, damage);
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
	    double horizontal = 16;
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