package net.chaos.chaosmod.entity;

import static net.minecraft.item.Item.getItemFromBlock;

import net.chaos.chaosmod.entity.ai.EntityAIMineGold;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.lore.dialogs.ITalkable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

/**
 * TODO : fix and refactor this entire class along with EntityAIStealBlock
 */
public class EntityPicsou extends EntityCreature implements ITalkable {
	private InventoryBasic inventory;

	public EntityPicsou(World worldIn) {
		super(worldIn);
		inventory = new InventoryBasic("picsou_inventory", false, 8);
		this.setCanPickUpLoot(true);
		this.experienceValue = 100;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10);
		this.getEntityAttribute(SWIM_SPEED).setBaseValue(10);
	}

	@Override
	protected void onDeathUpdate() {
		super.onDeathUpdate();

		dropAllItems();
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();

		this.tasks.addTask(0, new EntityAISwimming(this));
		// this.tasks.addTask(0, new EntityAIStealBlock(this, 10.0, 16));
		this.tasks.addTask(1, new EntityAIMineGold(this, 3.0, 16));
	}

	@Override
	protected ResourceLocation getLootTable() {
		return new ResourceLocation(Reference.MODID, "dungeon_loot");
	}

	@Override
	public void updateEquipmentIfNeeded(EntityItem itemEntity) {
		ItemStack stack = itemEntity.getItem();

		// Try to insert into custom inventory first
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack slotStack = inventory.getStackInSlot(i);

			if (slotStack.isEmpty()) {
				this.inventory.setInventorySlotContents(i, stack.copy());
				itemEntity.setDead();
				return;
			} else if (ItemStack.areItemsEqual(slotStack, stack) && ItemStack.areItemStackTagsEqual(slotStack, stack)
					&& slotStack.getCount() < slotStack.getMaxStackSize()) {

				int transferAmount = Math.min(stack.getCount(), slotStack.getMaxStackSize() - slotStack.getCount());
				slotStack.grow(transferAmount);
				stack.shrink(transferAmount);

				if (stack.isEmpty()) {
					itemEntity.setDead();
					return;
				}
			}
		}

		// Fallback to vanilla behavior if custom inventory is full
		super.updateEquipmentIfNeeded(itemEntity);
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	public boolean isItemInInventory() {
		for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
			ItemStack itemstack = this.inventory.getStackInSlot(i);

			if (!itemstack.isEmpty()) {
				Item item = itemstack.getItem();
				return isItemWanted(item);
			}
		}

		return false;
	}

	public boolean wantsMoreLoot() {
		// TODO
		// boolean flag = this.getProfession() == 0; isFilou ?
		boolean flag = true;

		if (flag) {
			return !this.hasEnoughItems(5);
		} else {
			return !this.hasEnoughItems(1);
		}
	}

	/**
	 * Returns true if picsou has enough items in inventory
	 */
	private boolean hasEnoughItems(int multiplier) {
		// boolean flag = this.getProfession() == 0;

		for (int i = 0; i < this.inventory.getSizeInventory(); ++i) {
			ItemStack itemstack = this.inventory.getStackInSlot(i);

			if (!itemstack.isEmpty()) {
				if ((itemstack.getItem() == getItemFromBlock(Blocks.DIAMOND_BLOCK)
						&& itemstack.getCount() >= 1 * multiplier)
						|| (itemstack.getItem() == getItemFromBlock(ModBlocks.ENDERITE_BLOCK)
								&& itemstack.getCount() >= 1 * multiplier)
						|| (itemstack.getItem() == getItemFromBlock(ModBlocks.OXONIUM_BLOCK)
								&& itemstack.getCount() >= 2 * multiplier)
						|| (itemstack.getItem() == getItemFromBlock(Blocks.GOLD_BLOCK)
								&& itemstack.getCount() >= 12 * multiplier)) {
					return true;
				}
			}
		}

		return false;
	}

	private void dropAllItems() {
		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (!stack.isEmpty()) {
				entityDropItem(stack.copy(), 0);
				inventory.setInventorySlotContents(i, ItemStack.EMPTY);
			}
		}
	}

	private boolean isItemWanted(Item item) {
		return item == getItemFromBlock(ModBlocks.OXONIUM_BLOCK)
				|| item == getItemFromBlock(ModBlocks.ALLEMANITE_BLOCK)
				|| item == getItemFromBlock(ModBlocks.ENDERITE_BLOCK)
				|| item == getItemFromBlock(Blocks.GOLD_BLOCK)
				|| item == getItemFromBlock(Blocks.DIAMOND_BLOCK)
				|| item == getItemFromBlock(Blocks.LAPIS_BLOCK)
				|| item == getItemFromBlock(Blocks.GLOWSTONE);
	}

	public InventoryBasic getInventory() {
		return inventory;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getDialogText() {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if (player == null || player.isCreative())
			return "";

		// Optional distance check
		if (this.getDistanceSq(player) > 40)
			return "";
		return "You owe me gold blocks...";
	}
}
