package net.chaos.chaosmod.entity.projectile;

import net.chaos.chaosmod.init.ModDamageSources;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class EntityMenhir extends EntityRock {

	public EntityMenhir(World worldIn) {
		super(worldIn);
		// TODO Auto-generated constructor stub
	}

	public EntityMenhir(World worldIn, EntityLivingBase throwerIn) {
        super(worldIn, throwerIn);
    }

    public EntityMenhir(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }
    
    @Override
    protected void onImpact(RayTraceResult result) {
        if (!world.isRemote) {
            // Deal damage or effects here
            this.world.setEntityState(this, (byte)3); // For hit particles
            if (result.typeOfHit == Type.ENTITY) {
            	float damage = 4.0f * 2; // 1 heart by default
            	if (result.entityHit instanceof EntityPlayer) {
            		EntityPlayer player = (EntityPlayer) result.entityHit;
            		Item item = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
            		if (item != ModItems.OXONIUM_HELMET) {
            			damage = 4.0f * 2;
            		} else {
            			damage = 4.0f * 2 * 0.6f;
            		}
            	}
            	result.entityHit.attackEntityFrom(ModDamageSources.MENHIR_DAMAGE, damage);
            }
            this.setDead();
        }
    }

    @Override
    public void shoot(double x, double y, double z, float velocity, float inaccuracy) {
    	super.shoot(x, y, z, velocity, inaccuracy);
    }

}
