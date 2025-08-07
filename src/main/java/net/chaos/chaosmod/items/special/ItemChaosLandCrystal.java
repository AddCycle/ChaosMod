package net.chaos.chaosmod.items.special;

import java.util.List;

import net.chaos.chaosmod.entity.boss.fightmanager.CMFightManager;
import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.world.structures.DimensionProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemChaosLandCrystal extends ItemBase {

	public ItemChaosLandCrystal(String name) {
		super(name);
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos);

		if (iblockstate.getBlock() != Blocks.OBSIDIAN && iblockstate.getBlock() != Blocks.BEDROCK)
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			BlockPos blockpos = pos.up();
			ItemStack itemstack = player.getHeldItem(hand);

			if (!player.canPlayerEdit(blockpos, facing, itemstack))
			{
				return EnumActionResult.FAIL;
			}
			else
			{
				BlockPos blockpos1 = blockpos.up();
				boolean flag = !worldIn.isAirBlock(blockpos) && !worldIn.getBlockState(blockpos).getBlock().isReplaceable(worldIn, blockpos);
				flag = flag | (!worldIn.isAirBlock(blockpos1) && !worldIn.getBlockState(blockpos1).getBlock().isReplaceable(worldIn, blockpos1));

				if (flag)
				{
					return EnumActionResult.FAIL;
				}
				else
				{
					double d0 = (double)blockpos.getX();
					double d1 = (double)blockpos.getY();
					double d2 = (double)blockpos.getZ();
					List<Entity> list = worldIn.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(d0, d1, d2, d0 + 1.0D, d1 + 2.0D, d2 + 1.0D));

					if (!list.isEmpty())
					{
						return EnumActionResult.FAIL;
					}
					else
					{
						if (!worldIn.isRemote)
						{
							EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(worldIn, (double)((float)pos.getX() + 0.5F), (double)(pos.getY() + 1), (double)((float)pos.getZ() + 0.5F));
							entityendercrystal.setShowBottom(false);
							worldIn.spawnEntity(entityendercrystal);

							if (worldIn.provider instanceof DimensionProvider)
							{
								CMFightManager chaosmasterfightmanager = (CMFightManager) ((DimensionProvider)worldIn.provider).getDragonFightManager();
								chaosmasterfightmanager.respawnDragon();
							}
						}

						itemstack.shrink(1);
						return EnumActionResult.SUCCESS;
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}
}