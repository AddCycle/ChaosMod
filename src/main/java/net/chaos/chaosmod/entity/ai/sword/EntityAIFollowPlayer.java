package net.chaos.chaosmod.entity.ai.sword;

import net.chaos.chaosmod.entity.EntitySwordOfWrath;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.Vec3d;

public class EntityAIFollowPlayer extends EntityAIBase {
	private final EntitySwordOfWrath entity;
	private final double speed;

	public EntityAIFollowPlayer(EntitySwordOfWrath entity, double speed) {
		this.entity = entity;
		this.speed = speed;
		this.setMutexBits(1); // 1 for motion
	}

	@Override
	public boolean shouldExecute() {
		return entity.getOwner() != null;
	}

	@Override
	public void updateTask() {
		EntityLivingBase owner = entity.getOwner();
		if (owner == null) return;

		Vec3d look = owner.getLookVec();
		Vec3d side = look.crossProduct(new Vec3d(0, 1, 0)).normalize();

		double offset = 2.0;

		double targetX = owner.posX + side.x * offset;
		double targetY = owner.posY;
		double targetZ = owner.posZ + side.z * offset;

		double dx = targetX - entity.posX;
		double dy = targetY - entity.posY;
		double dz = targetZ - entity.posZ;

		double dist = Math.sqrt(dx*dx + dy*dy + dz*dz);

		if (dist > 1) {

		    dx /= dist;
		    dy /= dist;
		    dz /= dist;

		    entity.motionX = dx * speed;
		    entity.motionY = dy * speed;
		    entity.motionZ = dz * speed;
		} else {
		    entity.motionX *= 0.5;
		    entity.motionY *= 0.5;
		    entity.motionZ *= 0.5;
		}

		if (dist <= 1 && dist > 0.1) {
		    double speed = 0.15;

		    dx /= dist;
		    dy /= dist;
		    dz /= dist;

		    entity.motionX = dx * speed;
		    entity.motionY = dy * speed;
		    entity.motionZ = dz * speed;
		} else {
		    entity.motionX *= 0.5;
		    entity.motionY *= 0.5;
		    entity.motionZ *= 0.5;
		}
	}
}
