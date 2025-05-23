package net.chaos.chaosmod.items.tools;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OxoniumHalleberd extends ToolSword {

	public OxoniumHalleberd(String name, ToolMaterial material) {
		super(name, material);
		setMaxStackSize(1);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			EntityLightningBolt bolt = new EntityLightningBolt(worldIn, pos.getX(), pos.getY(), pos.getZ(), true);
			worldIn.createExplosion(player, pos.getX(), pos.getY() + 1, pos.getZ(), 2.0f, false);
			worldIn.spawnEntity(bolt);
		}
		return EnumActionResult.SUCCESS;
	}

}
