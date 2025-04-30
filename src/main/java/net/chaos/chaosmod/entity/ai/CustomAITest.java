package net.chaos.chaosmod.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

public class CustomAITest extends EntityAIBase {
	private final EntityCreature creature;
    private double x, y, z;
    private final double speed;

    public CustomAITest(EntityCreature creature, double speed) {
        this.creature = creature;
        this.speed = speed;
        this.setMutexBits(1);
    }

    @Override
    public boolean shouldExecute() {
        if (creature.ticksExisted >= 100) return false;

        Vec3d vec = RandomPositionGenerator.findRandomTarget(creature, 10, 7);
        if (vec == null) return false;

        this.x = vec.x;
        this.y = vec.y;
        this.z = vec.z;
        return true;
    }

    @Override
    public void startExecuting() {
        creature.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
    }

}
