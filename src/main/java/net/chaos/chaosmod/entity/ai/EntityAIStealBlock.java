package net.chaos.chaosmod.entity.ai;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

/*
 * For now only working with EntityPicsou
 */
public class EntityAIStealBlock extends CustomAIMoveToBlock
{
	/** Creature that is stealing */
    private final EntityPicsou creature; // FIXME : change to any entity
    private boolean hasItem;
    private boolean wantsToReapStuff;
    /** 0 => steal, 1 => give it back, -1 => flee away with the stolen thing */
    private int currentTask;

    public EntityAIStealBlock(EntityCreature creature, double speedIn, int length) {
		super(creature, speedIn, length);
        this.creature = (EntityPicsou) creature; // idem check the type
	}

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.runDelay <= 0)
        {
            if (!ForgeEventFactory.getMobGriefingEvent(this.creature.world, this.creature))
            {
                return false;
            }

            this.currentTask = -1;
            this.hasItem = this.creature.isItemInInventory();
            this.wantsToReapStuff = this.creature.wantsMoreLoot();
        }

        return super.shouldExecute();
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.currentTask >= 0 && super.shouldContinueExecuting();
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        super.updateTask();
        this.creature.getLookHelper().setLookPosition((double)this.destinationBlock.getX() + 0.5D, (double)(this.destinationBlock.getY() + 1), (double)this.destinationBlock.getZ() + 0.5D, 10.0F, (float)this.creature.getVerticalFaceSpeed());

        if (this.getIsAboveDestination())
        {
            World world = this.creature.world;

            // Decide which block position to interact with
            BlockPos blockpos = (this.currentTask == 0)
                ? this.destinationBlock       // break this block directly
                : this.destinationBlock.up(); // place above

            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            Main.getLogger().debug("PIXOU is above destination : {}", this.destinationBlock);
            Main.getLogger().debug("The Block : {}", block.getLocalizedName());

            if (this.currentTask == 0 && block == Blocks.GOLD_BLOCK)
            {
                world.destroyBlock(blockpos, true);
                Main.getLogger().debug("Block broken by picsou");
            }
            else if (this.currentTask == 1 && iblockstate.getMaterial() == Material.AIR)
            {
                // Your place-item logic here...
            }

            this.currentTask = -1;
            this.runDelay = 1; // stealing cooldown
        }
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(World worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();
    	Main.getLogger().debug("BLK : {}", block);
    	if (block == Blocks.GOLD_BLOCK)
    	{
    	    if (this.wantsToReapStuff && (this.currentTask == 0 || this.currentTask < 0))
    	    {
    	        // Steal task → go directly to this block
    	        this.currentTask = 0;
    	        return true;
    	    }

    	    // Place-back task → check above
    	    BlockPos above = pos.up();
    	    IBlockState aboveState = worldIn.getBlockState(above);

    	    if (aboveState.getMaterial() == Material.AIR && this.hasItem && (this.currentTask == 1 || this.currentTask < 0))
    	    {
    	        this.currentTask = 1;
    	        return true;
    	    }
    	}
        
        return false;
    }
}
