package net.chaos.chaosmod.entity.ai.sword;

import net.chaos.chaosmod.entity.EntitySwordOfWrath;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.DamageSource;

public class EntityAIAttackTarget extends EntityAIBase {
	private final EntitySwordOfWrath entity;

	public EntityAIAttackTarget(EntitySwordOfWrath entity) {
		this.entity = entity;
	}

	@Override
	public boolean shouldExecute() {
		return entity.getAttackTarget() != null;
	}

	@Override
	public void updateTask() {
		EntityLivingBase target = entity.getAttackTarget();
		if (target == null)
			return;

		double dx = target.posX - entity.posX;
		double dy = target.posY - entity.posY;
		double dz = target.posZ - entity.posZ;

		double dist = Math.sqrt(dx * dx + dy * dy + dz * dz);

		if (dist > 1.5) {
			double speed = 0.25;

			entity.motionX = dx / dist * speed;
			entity.motionY = dy / dist * speed;
			entity.motionZ = dz / dist * speed;
		} else {
			target.attackEntityFrom(DamageSource.causeMobDamage(entity), 4.0f);
			entity.attack(); // animation
		}
	}
}