package net.chaos.chaosmod.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ChaosMasterFireball extends EntityDragonFireball {

	public ChaosMasterFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
		super(worldIn, shooter, accelX, accelY, accelZ);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {

        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            this.markVelocityChanged();

            if (source.getTrueSource() != null)
            {
                Vec3d vec3d = source.getTrueSource().getLookVec();

                if (vec3d != null)
                {
                    this.motionX = vec3d.x;
                    this.motionY = vec3d.y;
                    this.motionZ = vec3d.z;
                    this.accelerationX = this.motionX * 0.1D;
                    this.accelerationY = this.motionY * 0.1D;
                    this.accelerationZ = this.motionZ * 0.1D;
                }

                if (source.getTrueSource() instanceof EntityLivingBase)
                {
                    this.shootingEntity = (EntityLivingBase)source.getTrueSource();
                }

                return true;
            }
            else
            {
                return false;
            }
        }
	}
	
	@Override
	// FIXME : I gave up on this, only being able to reflect it with bow (later...)
	protected void onImpact(RayTraceResult result) {
		super.onImpact(result);
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
}