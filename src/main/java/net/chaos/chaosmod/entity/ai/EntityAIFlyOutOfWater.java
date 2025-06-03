package net.chaos.chaosmod.entity.ai;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityAIFlyOutOfWater extends EntityAIBase {
    private final EntityLiving entity;
    private final double speed;
    private Vec3d escapeTarget;

    public EntityAIFlyOutOfWater(EntityLiving entity, double speed) {
        this.entity = entity;
        this.speed = speed;
        this.setMutexBits(1); // Controls movement
    }

    @Override
    public boolean shouldExecute() {
        return entity.isInWater() || isInFlowingWater();
    }

    @Override
    public void startExecuting() {
        escapeTarget = findEscapeTarget();
        if (escapeTarget != null) {
            entity.getMoveHelper().setMoveTo(escapeTarget.x, escapeTarget.y, escapeTarget.z, speed);
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return (entity.isInWater() || isInFlowingWater()) && escapeTarget != null;
    }

    private boolean isInFlowingWater() {
        IBlockState state = entity.world.getBlockState(new BlockPos(entity));
        return state.getMaterial() == Material.WATER && state.getBlock() instanceof BlockLiquid &&
               ((BlockLiquid) state.getBlock()).getMetaFromState(state) > 0;
    }

    private Vec3d findEscapeTarget() {
        Vec3d currentPos = entity.getPositionVector();

        for (int i = 0; i < 20; i++) {
            Vec3d randomOffset = new Vec3d(
                entity.getRNG().nextDouble() * 10 - 5,
                entity.getRNG().nextDouble() * 4 + 1, // always prefer up
                entity.getRNG().nextDouble() * 10 - 5
            );
            BlockPos testPos = new BlockPos(currentPos.add(randomOffset));
            IBlockState state = entity.world.getBlockState(testPos);

            if (!state.getMaterial().isLiquid() && entity.world.isAirBlock(testPos)) {
                return new Vec3d(testPos).add(new Vec3d(0.5, 0.5, 0.5)); // center of block
            }
        }
        return null;
    }
}