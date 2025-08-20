package net.chaos.chaosmod.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class EntityBipedBasic extends EntityLiving {

	public EntityBipedBasic(World worldIn) {
		super(worldIn);
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
	}

	@Override
	public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
		return ItemStack.EMPTY;
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
		// TODO Auto-generated method stub
	}

	@Override
	public EnumHandSide getPrimaryHand() {
		return EnumHandSide.RIGHT;
	}

}
