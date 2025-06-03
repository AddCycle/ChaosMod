package net.chaos.chaosmod.entity.ai;

import net.minecraft.block.state.IBlockState;
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
        this.setMutexBits(1); // Movement
    }

    @Override
    public boolean shouldExecute() {
        if (!entity.isInWater()) return false;

        // Try to find a nearby non-water position
        int r = 10; // range
        BlockPos entityPos = new BlockPos(entity);
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                for (int dz = -r; dz <= r; dz++) {
                    BlockPos checkPos = entityPos.add(dx, dy, dz);
                    IBlockState state = entity.world.getBlockState(checkPos);
                    if (!state.getMaterial().isLiquid() && entity.world.isAirBlock(checkPos.up())) {
                        targetPos = checkPos;
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return entity.isInWater() && !entity.getNavigator().noPath();
    }

    @Override
    public void startExecuting() {
        if (targetPos != null) {
            entity.getNavigator().tryMoveToXYZ(targetPos.getX(), targetPos.getY(), targetPos.getZ(), speed);
        }
    }
}