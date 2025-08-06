package net.chaos.chaosmod.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OxoniumHalleberd extends ToolSword {
	public boolean isCharged = false;
	public int cooldown = 100; // 5sec
	public int animationCount = 0;

	public OxoniumHalleberd(String name, ToolMaterial material) {
		super(name, material);
		setMaxStackSize(1);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		return isCharged;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (isCharged && !worldIn.isRemote) {
			EntityLightningBolt bolt = new EntityLightningBolt(worldIn, pos.getX(), pos.getY(), pos.getZ(), true);
			worldIn.createExplosion(player, pos.getX(), pos.getY() + 1, pos.getZ(), 2.0f, false);
			worldIn.spawnEntity(bolt);
			isCharged = false;
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!isCharged && !worldIn.isRemote) {
			animationCount++;
			if (animationCount >= cooldown) {
				isCharged = true;
				animationCount = 0;
			}
		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

}
