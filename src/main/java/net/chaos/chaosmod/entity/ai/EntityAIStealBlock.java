package net.chaos.chaosmod.entity.ai;

import net.chaos.chaosmod.entity.EntityPicsou;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/*
 * For now only working with EntityPicsou
 */
public class EntityAIStealBlock extends EntityAIMoveToBlock
{
	/** Creature that is stealing */
    private final EntityPicsou creature;
    private boolean hasItem;
    private boolean wantsToReapStuff;
    /** 0 => steal, 1 => give it back, -1 => flee away with the stolen thing */
    private int currentTask;

    public EntityAIStealBlock(EntityCreature creature, double speedIn, int length) {
		super(creature, speedIn, length);
        this.creature = (EntityPicsou) creature;
	}

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.runDelay <= 0)
        {
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.creature.world, this.creature))
            {
                return false;
            }

            this.currentTask = -1;
            this.hasItem = this.creature.isItemInInventory(); // TODO
            this.wantsToReapStuff = this.creature.wantsMoreLoot(); // TODO
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
        	System.out.println("Picsou is above");
            World world = this.creature.world;
            BlockPos blockpos = this.destinationBlock.up();
            IBlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            // if (this.currentTask == 0 && block instanceof BlockCrops && ((BlockCrops)block).isMaxAge(iblockstate))
            if (this.currentTask == 0 && block == Blocks.GOLD_BLOCK)
            {
                world.destroyBlock(blockpos, true);
                System.out.println("Block stolen by picsou");
            }
            else if (this.currentTask == 1 && iblockstate.getMaterial() == Material.AIR)
            {
                InventoryBasic inventorybasic = this.creature.getInventory(); // DONE

                for (int i = 0; i < inventorybasic.getSizeInventory(); ++i)
                {
                    ItemStack itemstack = inventorybasic.getStackInSlot(i);
                    boolean flag = false;

                    if (!itemstack.isEmpty())
                    {
                        if (itemstack.getItem() == Items.WHEAT_SEEDS)
                        {
                            world.setBlockState(blockpos, Blocks.WHEAT.getDefaultState(), 3);
                            flag = true;
                        }
                        else if (itemstack.getItem() == Items.POTATO)
                        {
                            world.setBlockState(blockpos, Blocks.POTATOES.getDefaultState(), 3);
                            flag = true;
                        }
                        else if (itemstack.getItem() == Items.CARROT)
                        {
                            world.setBlockState(blockpos, Blocks.CARROTS.getDefaultState(), 3);
                            flag = true;
                        }
                        else if (itemstack.getItem() == Items.BEETROOT_SEEDS)
                        {
                            world.setBlockState(blockpos, Blocks.BEETROOTS.getDefaultState(), 3);
                            flag = true;
                        }
                        else if (itemstack.getItem() instanceof net.minecraftforge.common.IPlantable) {
                            if(((net.minecraftforge.common.IPlantable)itemstack.getItem()).getPlantType(world,blockpos) == net.minecraftforge.common.EnumPlantType.Crop) {
                                world.setBlockState(blockpos, ((net.minecraftforge.common.IPlantable)itemstack.getItem()).getPlant(world,blockpos),3);
                                flag = true;
                            }
                        }
                    }

                    if (flag)
                    {
                        itemstack.shrink(1);

                        if (itemstack.isEmpty())
                        {
                            inventorybasic.setInventorySlotContents(i, ItemStack.EMPTY);
                        }

                        break;
                    }
                }
            }

            this.currentTask = -1;
            this.runDelay = 10;
        }
    }

    /**
     * Return true to set given position as destination
     */
    protected boolean shouldMoveTo(World worldIn, BlockPos pos)
    {
        Block block = worldIn.getBlockState(pos).getBlock();

        // if (block == Blocks.FARMLAND)
        if (block == Blocks.GOLD_BLOCK)
        {
            pos = pos.up();
            IBlockState iblockstate = worldIn.getBlockState(pos);
            block = iblockstate.getBlock();

            // if (block instanceof BlockCrops && ((BlockCrops)block).isMaxAge(iblockstate) && this.wantsToReapStuff && (this.currentTask == 0 || this.currentTask < 0))
            if (block == Blocks.GOLD_BLOCK && (this.currentTask == 0 || this.currentTask < 0))
            {
            	System.out.println("Picsou is moving...");
                this.currentTask = 0;
                return true;
            }

            /*if (iblockstate.getMaterial() == Material.AIR && this.hasItem && (this.currentTask == 1 || this.currentTask < 0))
            {
                this.currentTask = 1;
                return true;
            }*/
        }

        return false;
    }
}
