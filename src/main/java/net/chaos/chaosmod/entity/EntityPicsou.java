package net.chaos.chaosmod.entity;

import net.chaos.chaosmod.entity.ai.EntityAIStealBlock;
import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityPicsou extends EntityCreature {
	private InventoryBasic inventory;

	public EntityPicsou(World worldIn) {
		super(worldIn);
		inventory = new InventoryBasic("test", false, 8);
		this.setCanPickUpLoot(true);
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10);
		this.getEntityAttribute(SWIM_SPEED).setBaseValue(10);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(0, new EntityAIStealBlock(this, 3.0, 100));
		// this.tasks.addTask(1, new EntityAILookIdle(this));
		// TODO Combine those two below into one as harvest farmland AI
		/*this.tasks.addTask(0, new EntityAIMoveToBlock(this, 3.0, 10) {
			@Override
			protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
				Block actual = worldIn.getBlockState(pos).getBlock();
				return actual instanceof OxoniumBlock
					|| actual instanceof AllemaniteBlock
					|| actual instanceof EnderiteBlock
					|| actual == Blocks.GOLD_BLOCK
					|| actual == Blocks.DIAMOND_BLOCK
					|| actual == Blocks.LAPIS_BLOCK
					|| actual == Blocks.GLOWSTONE;
			}
		});*/
	}
	
	@Override
	protected boolean canDespawn() {
		return false;
	}

	public boolean isItemInInventory()
    {
        for (int i = 0; i < this.inventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.inventory.getStackInSlot(i);

            // TODO : refactor that in constructor, init an arrayList or something static and check if it's in it
            if (!itemstack.isEmpty())
            {
            	Item item = itemstack.getItem();
				return item == Item.getItemFromBlock(ModBlocks.OXONIUM_BLOCK)
					|| item == Item.getItemFromBlock(ModBlocks.ALLEMANITE_BLOCK)
					|| item == Item.getItemFromBlock(ModBlocks.ENDERITE_BLOCK)
					|| item == Item.getItemFromBlock(Blocks.GOLD_BLOCK)
					|| item == Item.getItemFromBlock(Blocks.DIAMOND_BLOCK)
					|| item == Item.getItemFromBlock(Blocks.LAPIS_BLOCK)
					|| item == Item.getItemFromBlock(Blocks.GLOWSTONE);
            }
        }

        return false;
    }


	public boolean wantsMoreLoot()
    {
		// TODO
        // boolean flag = this.getProfession() == 0; isFilou ?
		boolean flag = true;

        if (flag)
        {
            return !this.hasEnoughItems(5);
        }
        else
        {
            return !this.hasEnoughItems(1);
        }
    }

	/**
     * Returns true if villager has enough items in inventory
     */
	// TODO : modify with actual precious blocks
    private boolean hasEnoughItems(int multiplier)
    {
        // boolean flag = this.getProfession() == 0;

        for (int i = 0; i < this.inventory.getSizeInventory(); ++i)
        {
            ItemStack itemstack = this.inventory.getStackInSlot(i);

            if (!itemstack.isEmpty())
            {
                // if (itemstack.getItem() == Items.BREAD && itemstack.getCount() >= 3 * multiplier || itemstack.getItem() == Items.POTATO && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == Items.CARROT && itemstack.getCount() >= 12 * multiplier || itemstack.getItem() == Items.BEETROOT && itemstack.getCount() >= 12 * multiplier)
            	if (itemstack.getItem() == Item.getItemFromBlock(Blocks.DIAMOND_BLOCK) && itemstack.getCount() >= 1 * multiplier || itemstack.getItem() == Item.getItemFromBlock(ModBlocks.ENDERITE_BLOCK) && itemstack.getCount() >= 1 * multiplier || itemstack.getItem() == Item.getItemFromBlock(ModBlocks.OXONIUM_BLOCK) && itemstack.getCount() >= 2 * multiplier || itemstack.getItem() == Item.getItemFromBlock(Blocks.GOLD_BLOCK) && itemstack.getCount() >= 12 * multiplier)
                {
                    return true;
                }

                // if (flag && itemstack.getItem() == Items.WHEAT && itemstack.getCount() >= 9 * multiplier)
                if (itemstack.getItem() == Items.WHEAT && itemstack.getCount() >= 9 * multiplier)
                {
                    return true;
                }
            }
        }

        return false;
    }
	
	public InventoryBasic getInventory() {
		return inventory;
	}
}
