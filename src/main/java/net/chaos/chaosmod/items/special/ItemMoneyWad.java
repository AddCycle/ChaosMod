package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.common.capabilities.IMoney;
import net.chaos.chaosmod.common.capabilities.MoneyProvider;
import net.chaos.chaosmod.items.AbstractItemMetadatas;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class ItemMoneyWad extends AbstractItemMetadatas {

	public ItemMoneyWad(String name) {
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		if (!worldIn.isRemote) {
			IMoney moneyCap = playerIn.getCapability(MoneyProvider.MONEY_CAPABILITY, null);
			if (moneyCap != null) {
				int meta = this.getMetadata(stack);
				int value = 0;
	            switch (meta) {
	                case 0: value = 1000; break;
	                case 1: value = 2000; break;
	                case 2: value = 5000; break;
	            }
				moneyCap.add(value);
				stack.shrink(1);
				playerIn.sendMessage(new TextComponentString("Added " + value + " money!"));
			}
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
	}

}
