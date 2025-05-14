package net.chaos.chaosmod.entity.projectile;

import net.chaos.chaosmod.init.ModDamageSources;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

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
            	float damage = 4.0f;
            	if (result.entityHit instanceof EntityPlayer) {
            		EntityPlayer player = (EntityPlayer) result.entityHit;
            		Item item = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
            		if (item != ModItems.OXONIUM_HELMET) {
            			damage = 4.0f;
            		} else {
            			damage = 4.0f * 0.7f;
            		}
            	}
            	result.entityHit.attackEntityFrom(ModDamageSources.ROCK_DAMAGE, damage);
            }
            this.setDead();
        }
    }

    @Override
    protected float getGravityVelocity() {
        return 0.03F; // Default is 0.03F for snowballs; use higher for faster fall
    }

    // Optional: override speed if needed
    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
        super.shoot(x, y, z, velocity / 2, inaccuracy); // velocity / 2
    }

}
