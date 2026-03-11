package net.chaos.chaosmod.items.shield;

import net.chaos.chaosmod.client.inventory.shield.ShieldInventory;
import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class CustomItemShield extends ItemBase {

	public CustomItemShield(String name) {
		super(name);
		setMaxStackSize(1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack heldStack = playerIn.getHeldItem(handIn);

	    if (!worldIn.isRemote) {
	        ShieldInventory shieldInv = ShieldInventory.fromPlayer(playerIn);
	        ItemStack slotStack = shieldInv.getStackInSlot(0);

	        if (slotStack.isEmpty()) {
	            shieldInv.setInventorySlotContents(0, heldStack.copy());
	            heldStack.setCount(0);
	            return new ActionResult<>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
	        }
	    }

	    return new ActionResult<>(EnumActionResult.PASS, heldStack);
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
	{
		return new CreativeTabs[]{ ModTabs.ITEMS, CreativeTabs.COMBAT, CreativeTabs.SEARCH };
	}
}