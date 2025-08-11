package net.chaos.chaosmod.entity.ai;

import java.util.List;

import net.chaos.chaosmod.entity.EntityPicsou;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIMineGold extends CustomAIMoveToBlock {
    private final EntityPicsou creature;
    private BlockPos lastBlock;

    public EntityAIMineGold(EntityCreature creature, double speed, int range) {
        super(creature, speed, range);
        this.creature = (EntityPicsou) creature;
    }

    @Override
    protected boolean shouldMoveTo(World world, BlockPos pos) {
        return pos != null && world.getBlockState(pos).getBlock() == Blocks.GOLD_BLOCK;
    }

    @Override
    public void updateTask() {
        super.updateTask();

        if (this.destinationBlock == null) {
            return;  // No target block, so nothing to do
        }

        // If we're at the destination, break and immediately search again
        if (this.creature.getDistanceSqToCenter(this.destinationBlock) < 2.0D) {
            World world = this.creature.world;
            BlockPos targetPos = this.destinationBlock;

            if (world.getBlockState(targetPos).getBlock() == Blocks.GOLD_BLOCK) {
                world.destroyBlock(targetPos, true); // Drop items
                collectNearbyItems();
                this.setLastBlock(targetPos);
            }

            // Instantly search for the next block, no cooldown
            if (!searchForDestination()) {
                this.destinationBlock = null;
            }
        }
    }

    private void collectNearbyItems() {
        List<EntityItem> items = this.creature.world.getEntitiesWithinAABB(
            EntityItem.class,
            this.creature.getEntityBoundingBox().grow(2.0D)
        );

        for (EntityItem itemEntity : items) {
            if (!itemEntity.isDead && !itemEntity.getItem().isEmpty()) {
                this.creature.updateEquipmentIfNeeded(itemEntity);
                this.creature.onItemPickup(itemEntity, itemEntity.getItem().getCount());
            }
        }
    }

    private boolean searchForDestination() {
        int horizontalRange = this.searchLength;
        int verticalRange = 1;
        BlockPos basePos = new BlockPos(this.creature);

        for (int dy = -verticalRange; dy <= verticalRange; ++dy) {
            for (int r = 0; r <= horizontalRange; ++r) {
                for (int dx = -r; dx <= r; ++dx) {
                    for (int dz = -r; dz <= r; ++dz) {
                        if (Math.abs(dx) != r && Math.abs(dz) != r)
                            continue;

                        BlockPos pos = basePos.add(dx, dy, dz);

                        if (this.creature.isWithinHomeDistanceFromPosition(pos)
                         && shouldMoveTo(this.creature.world, pos)) {
                            this.destinationBlock = pos;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

	public BlockPos getLastBlock() {
		return lastBlock;
	}

	public void setLastBlock(BlockPos lastBlock) {
		this.lastBlock = lastBlock;
	}
}