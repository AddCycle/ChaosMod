package net.chaos.chaosmod.items.special;

import javax.annotation.Nullable;

import net.chaos.chaosmod.items.ItemBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemMagnet extends ItemBase {

	public ItemMagnet(String name) {
		super(name);
		setMaxStackSize(1);
		this.addPropertyOverride(new ResourceLocation("active"), new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				NBTTagCompound tag = stack.getOrCreateSubCompound("data");
				return entityIn != null && tag != null && tag.getBoolean("active") ? 1.0F : 0.0F;
			}
		});
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			NBTTagCompound tag = stack.getOrCreateSubCompound("data");

			boolean active = tag.getBoolean("active");
			tag.setBoolean("active", !active);

			playerIn.sendMessage(new TextComponentString("Active: " + !active));
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (worldIn.isRemote)
			return;

		NBTTagCompound tag = stack.getOrCreateSubCompound("data");

		boolean active = tag.getBoolean("active");
		if (!active) return;

		int range = 5;
		for (EntityItem item : worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(
				entityIn.getPosition().add(-range, -range, -range), entityIn.getPosition().add(range, range, range)))) {
			double dx = entityIn.posX - item.posX;
			double dy = (entityIn.posY + entityIn.getEyeHeight() * 0.5) - item.posY;
			double dz = entityIn.posZ - item.posZ;

			double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

			if (distance > 0.1) {
			    double speed = Math.min(0.1, distance * 0.1);

			    item.motionX = item.motionX * 0.8 + (dx / distance * speed);
			    item.motionY = item.motionY * 0.8 + (dy / distance * speed);
			    item.motionZ = item.motionZ * 0.8 + (dz / distance * speed);

			    item.velocityChanged = true;
			}
		}
	}
}