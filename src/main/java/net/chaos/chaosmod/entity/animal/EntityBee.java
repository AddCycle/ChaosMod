package net.chaos.chaosmod.entity.animal;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * TODO : make this tameable later on or maybe not if there is too many
 */
public class EntityBee extends EntityAnimal {
	private static final DataParameter<Boolean> ANGRY = EntityDataManager.<Boolean>createKey(EntityBee.class,
			DataSerializers.BOOLEAN);

	public EntityBee(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.7F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ANGRY, Boolean.valueOf(false));
	}

	@Override
	protected void initEntityAI() {}

	// FIXME : make angry logic inside AI I think (like if has a revenge entity)
	@Override
	protected void damageEntity(DamageSource damageSrc, float damageAmount) {
		super.damageEntity(damageSrc, damageAmount);
		this.setAngry(true);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	public void setAngry(boolean b) {
		this.dataManager.set(ANGRY, Boolean.valueOf(b));
	}

	public boolean isAngry() { return this.dataManager.get(ANGRY).booleanValue(); }
}