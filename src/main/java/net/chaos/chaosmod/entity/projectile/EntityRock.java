package net.chaos.chaosmod.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import util.Reference;

public class EntityRock extends EntityThrowable {
	public EntityRock(World worldIn) {
        super(worldIn);
    }

    public EntityRock(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityRock(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            // Deal damage or effects here
            this.world.setEntityState(this, (byte)3); // For hit particles
            if (result.typeOfHit == Type.ENTITY) {
            	result.entityHit.attackEntityFrom(new DamageSource(Reference.MODID + ":rock"), 6000f);
            }
            this.setDead();
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.08F; // Default is 0.03F for snowballs; use higher for faster fall
    }

    // Optional: override speed if needed
    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity, inaccuracy);
    }

}
