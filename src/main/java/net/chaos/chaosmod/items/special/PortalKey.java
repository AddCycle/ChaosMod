package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.blocks.DimensionPortal;
import net.chaos.chaosmod.items.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class PortalKey extends ItemBase {

	public PortalKey(String name) {
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
	    RayTraceResult ray = this.rayTrace(worldIn, playerIn, false);

	    if (ray == null || ray.typeOfHit != RayTraceResult.Type.BLOCK)
	        return new ActionResult<>(EnumActionResult.PASS, stack);

	    BlockPos pos = ray.getBlockPos();

	    if (DimensionPortal.trySpawnPortal(worldIn, pos.up())) {
	        if (!playerIn.isCreative()) stack.shrink(1);
	        playerIn.swingArm(handIn);
	        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	    }

	    return new ActionResult<>(EnumActionResult.FAIL, stack);
	}

}
