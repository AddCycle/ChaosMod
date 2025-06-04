package net.chaos.chaosmod.entity;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import util.Reference;

public class EntityChaosSage extends EntityLiving {

	public EntityChaosSage(World worldIn) {
		super(worldIn);
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!this.world.isRemote)
		{
			System.out.println("Opening Chaos Sage GUI with entity ID: " + this.getEntityId());
			player.openGui(Main.instance, Reference.GUI_DOCS_ID, this.world, this.getEntityId(), 0, 0); // TODO : change this to docs + do it
		}
		return true;
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 4.0F));
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return NonNullList.withSize(4, ItemStack.EMPTY);
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
		// do nothing
	}

	@Override
	public EnumHandSide getPrimaryHand() {
		return EnumHandSide.RIGHT;
	}
}
