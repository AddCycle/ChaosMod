package net.chaos.chaosmod.entity.boss.entities;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraft.world.end.DragonFightManager;
public class EntityEyeCrystal extends EntityEnderCrystal {
	public int health;

	public EntityEyeCrystal(World worldIn) {
		super(worldIn);
		health = 10;
	}

	public EntityEyeCrystal(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
		health = 10;
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
                    this.health -= 0.5;
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
}
