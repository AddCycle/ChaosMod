package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.items.ItemBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnderiteThunder extends ItemBase {

	public EnderiteThunder(String name) {
		super(name);
		this.setMaxDamage(1);
		this.setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			EntityLightningBolt bolt = new EntityLightningBolt(worldIn, pos.getX(), pos.getY() + 0.5, pos.getZ(), false);
			worldIn.addWeatherEffect(bolt);
			ItemStack stack = player.getHeldItemMainhand();
			if (stack.getItem() == ModItems.ENDERITE_THUNDER) stack.shrink(1);
		}
		return EnumActionResult.SUCCESS;
	}

}
