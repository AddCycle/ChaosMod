package net.chaos.chaosmod.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;

public class EntityAIEscapeWater extends EntityAIBase {
    private final EntityCreature entity;
    private final double speed;
    private BlockPos targetPos;

    public EntityAIEscapeWater(EntityCreature entity, double speed) {
        this.entity = entity;
        this.speed = speed;
        this.setMutexBits(1); // movement
    }

    @Override
    public boolean shouldExecute() {
        if (!entity.isInWater()) return false;

        targetPos = findNearbyDryLand(entity.getPosition());
        return targetPos != null;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return !entity.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        if (targetPos != null) {
            entity.getNavigator().tryMoveToXYZ(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
        }
    }

    private BlockPos findNearbyDryLand(BlockPos origin) {
        for (int dx = -5; dx <= 5; dx++) {
            for (int dz = -5; dz <= 5; dz++) {
                BlockPos pos = origin.add(dx, 0, dz);
                BlockPos up = pos.up();
                if (!entity.world.isAirBlock(pos) &&
                    entity.world.isAirBlock(up) &&
                    !entity.world.getBlockState(up).getMaterial().isLiquid()) {
                    return up;
                }
            }
        }
        return null;
    }
}