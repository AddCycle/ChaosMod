package net.chaos.chaosmod.entity.boss.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
public class EntityEyeCrystal extends EntityEnderCrystal {
	private int health;
    public final BossInfoServer bossInfo;

	public EntityEyeCrystal(World worldIn) {
		super(worldIn);
		health = 10;
		bossInfo = new BossInfoServer(this.getDisplayName(), Color.PURPLE, Overlay.NOTCHED_10);
		this.bossInfo.setName(this.getDisplayName());
		this.setSize(1.5f, 3);
	}

	public EntityEyeCrystal(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
		health = 10;
		bossInfo = new BossInfoServer(this.getDisplayName(), Color.PURPLE, Overlay.NOTCHED_10);
		this.bossInfo.setName(this.getDisplayName());
		this.setSize(1.5f, 1.5f);
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
		// TODO Auto-generated method stub
		super.setBeamTarget(beamTarget);
	}
	
	@Override
	public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
		return super.getExplosionResistance(explosionIn, worldIn, pos, blockStateIn);
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityAlive()) { 
			health -= 0.5;
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
                    // this.health -= 0.5;
                    System.out.println("Health : " + this.health);
                    if (this.health <= 0) {
                    	this.setDead();
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
		this.bossInfo.addPlayer(player);
	}
	
	@Override
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}
	
	private float getHealth() {
		return health;
	}

	private float getMaxHealth() {
		return 10; // FIXME do it a non hard-coded way
	}
	
	@Override
	public void onUpdate() {
		if (this.isEntityAlive()) { 
			this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        }
		
		super.onUpdate();
	}
}
