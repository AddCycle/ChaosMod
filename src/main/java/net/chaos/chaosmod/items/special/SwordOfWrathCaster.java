package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.entity.EntitySwordOfWrath;
import net.chaos.chaosmod.items.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class SwordOfWrathCaster extends ItemBase {

	public SwordOfWrathCaster(String name) {
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ActionResult<ItemStack> result = new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));

		if (!worldIn.isRemote) {
			EntitySwordOfWrath sword = new EntitySwordOfWrath(worldIn);
			sword.setPosition(playerIn.posX, playerIn.posY, playerIn.posZ);
			sword.setOwnerId(playerIn.getUniqueID());
			worldIn.spawnEntity(sword);

			playerIn.getEntityData().setBoolean("sword_of_wrath_cast", true);
			playerIn.getHeldItem(handIn).shrink(1);
		}

        return result;
	}
}