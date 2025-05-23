package net.chaos.chaosmod.entity.projectile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import util.Reference;

public class EntitySmallBlueFireball extends EntitySmallFireball {

	public EntitySmallBlueFireball(World worldIn) {
		super(worldIn);
        this.setSize(0.3125F, 0.3125F); // same size as EntitySmallFireball
	}
	
    public EntitySmallBlueFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
        super(worldIn, shooter, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F); // same size as EntitySmallFireball

        // this.setLocationAndAngles(shooter.posX, shooter.posY + (double)(shooter.getEyeHeight() / 2.0F), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);

        // DO NOT normalize, just use small values directly
        // this.motionX = accelX * 0.005;
        // this.motionY = accelY * 0.005;
        // this.motionZ = accelZ * 0.005;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (!this.world.isRemote) {
            if (result.entityHit != null) {
                result.entityHit.attackEntityFrom(new DamageSource(Reference.MODID + "_damagesource_bluefire") {
                	@Override
                	public ITextComponent getDeathMessage(EntityLivingBase entityLivingBaseIn) {
                		// FIXME adding later random death messages
                		return new TextComponentTranslation("damagesource.bluefire.message_" + "1").setStyle(new Style().setBold(true).setColor(TextFormatting.AQUA));
                	}
                }, 12.0F); // twice damage
                if(!result.entityHit.isImmuneToFire()) result.entityHit.setFire(5); // FIXME now random burning time not too long
            }

            this.world.newExplosion(this, this.posX, this.posY, this.posZ, 0F, false, false);
            this.setDead();
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (world.isRemote) {
            // Blue fire particles
            for (int i = 0; i < 2; i++) {
                world.spawnParticle(EnumParticleTypes.END_ROD, 
                    posX + (rand.nextDouble() - 0.5) * 0.5,
                    posY + (rand.nextDouble() - 0.5) * 0.5,
                    posZ + (rand.nextDouble() - 0.5) * 0.5,
                    0, 0, 0);
            }
        }
    }
    
    @Override
    public boolean canRenderOnFire() {
    	return true;
    }
    
    @Override
    public boolean canExplosionDestroyBlock(Explosion explosionIn, World worldIn, BlockPos pos,
    		IBlockState blockStateIn, float p_174816_5_) {
    	return true;
    }

}
