package net.chaos.chaosmod.enchantments;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EnchantmentLavaWalker extends AbstractEnchantment {

	public EnchantmentLavaWalker(String name, Rarity rarityIn) {
		super(name, rarityIn, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[] { EntityEquipmentSlot.FEET });
	}

	@Override
	public boolean canApplyTogether(Enchantment ench) {
		return super.canApplyTogether(ench) && ench != Enchantments.DEPTH_STRIDER;
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}

	@Override
	public int getMinLevel() {
		return 1;
	}

	@Override
	public int getMinEnchantability(int level) {
		return level * 10;
	}

	@Override
	public int getMaxEnchantability(int level) {
		return this.getMinEnchantability(level) + 15;
	}

	public static void solidifyNearbyLava(EntityLivingBase living, World worldIn, BlockPos pos, int level) {
		if (!living.onGround)
			return;

		float f = (float) Math.min(16, 2 + level);
		BlockPos.MutableBlockPos mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

		for (BlockPos.MutableBlockPos mutableblockpos1 : BlockPos.getAllInBoxMutable(
				pos.add((double) (-f), -1.0D, (double) (-f)), pos.add((double) f, -1.0D, (double) f))) {
			if (mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double) (f * f)) {
				mutableblockpos.setPos(mutableblockpos1.getX(), mutableblockpos1.getY() + 1, mutableblockpos1.getZ());
				IBlockState iblockstate = worldIn.getBlockState(mutableblockpos);

				if (iblockstate.getMaterial() != Material.AIR)
					continue;

				IBlockState iblockstate1 = worldIn.getBlockState(mutableblockpos1);
				if (isLavaReplaceableByMagma(worldIn, mutableblockpos1, iblockstate1)) {
					worldIn.setBlockState(mutableblockpos1, Blocks.MAGMA.getDefaultState());
					worldIn.scheduleUpdate(mutableblockpos1.toImmutable(), Blocks.MAGMA,
							MathHelper.getInt(living.getRNG(), 60, 120));
				}
			}
		}
	}

	private static boolean isLavaReplaceableByMagma(World worldIn, MutableBlockPos mutableblockpos1,
			IBlockState iblockstate1) {
		return iblockstate1.getMaterial() == Material.LAVA
				&& (iblockstate1.getBlock() == Blocks.LAVA || iblockstate1.getBlock() == Blocks.FLOWING_LAVA)
				&& ((Integer) iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0
				&& worldIn.mayPlace(Blocks.MAGMA, mutableblockpos1, false, EnumFacing.DOWN, (Entity) null);
	}

}