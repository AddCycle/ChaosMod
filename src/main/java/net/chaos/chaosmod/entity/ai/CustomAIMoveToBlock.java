package net.chaos.chaosmod.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class CustomAIMoveToBlock extends EntityAIBase
{
    private final EntityCreature creature;
    private final double movementSpeed;
    /** Controls task execution delay */
    protected int runDelay;
    private int timeoutCounter;
    private int maxStayTicks;
    /** Block to move to */
    protected BlockPos destinationBlock = BlockPos.ORIGIN;
    private boolean isAboveDestination;
    protected final int searchLength;

    public CustomAIMoveToBlock(EntityCreature creature, double speedIn, int length)
    {
        this.creature = creature;
        this.movementSpeed = speedIn;
        this.searchLength = length;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.runDelay > 0)
        {
        	// Main.getLogger().info("delay : {}", this.runDelay);
            --this.runDelay;
            return false;
        }
        else
        {
        	int base_delay = 10;
        	int additional = 10;
            this.runDelay = base_delay + this.creature.getRNG().nextInt(additional);
            return this.searchForDestination();
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.timeoutCounter >= -this.maxStayTicks && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.creature.world, this.destinationBlock);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.creature.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
        this.timeoutCounter = 0;
        this.maxStayTicks = this.creature.getRNG().nextInt(this.creature.getRNG().nextInt(1200) + 1200) + 1200;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        if (this.creature.getDistanceSqToCenter(this.destinationBlock.up()) > 1.0D)
        {
            this.isAboveDestination = false;
            ++this.timeoutCounter;

            if (this.timeoutCounter % 40 == 0)
            {
                this.creature.getNavigator().tryMoveToXYZ((double)((float)this.destinationBlock.getX()) + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)((float)this.destinationBlock.getZ()) + 0.5D, this.movementSpeed);
            }
        }
        else
        {
            this.isAboveDestination = true;
            --this.timeoutCounter;
        }
    }

    protected boolean getIsAboveDestination()
    {
        return this.isAboveDestination;
    }

    /**
     * Searches and sets new destination block and returns true if a suitable block (specified in {@link
     * net.minecraft.entity.ai.EntityAIMoveToBlock#shouldMoveTo(World, BlockPos) EntityAIMoveToBlock#shouldMoveTo(World,
     * BlockPos)}) can be found.
     */
    private boolean searchForDestination()
    {
        // Main.getLogger().info("PICSOU SEARCHING FOR SUITABLE BLOCK...");

        int horizontalRange = this.searchLength;
        int verticalRange = 1;
        BlockPos basePos = new BlockPos(this.creature);

        // Loop vertically first
        for (int dy = -verticalRange; dy <= verticalRange; ++dy)
        {
            for (int r = 0; r <= horizontalRange; ++r)
            {
                for (int dx = -r; dx <= r; ++dx)
                {
                    for (int dz = -r; dz <= r; ++dz)
                    {
                        // Skip positions that aren't on the edge of the current square radius
                        if (Math.abs(dx) != r && Math.abs(dz) != r)
                            continue;

                        BlockPos pos = basePos.add(dx, dy, dz);

                        // Main.getLogger().info("Checking: {}", pos);

                        if (this.creature.isWithinHomeDistanceFromPosition(pos)
                         && this.shouldMoveTo(this.creature.world, pos))
                        {
                            this.destinationBlock = pos;
                            // Main.getLogger().info("FOUND: {}", pos);
                            return true;
                        }
                    }
                }
            }
        }

        // Main.getLogger().info("NOT FOUND, RETRY!");
        return false;
    }

    /**
     * Return true to set given position as destination
     */
    protected abstract boolean shouldMoveTo(World worldIn, BlockPos pos);
}