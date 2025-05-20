package net.chaos.chaosmod.items.necklace;

import net.chaos.chaosmod.client.inventory.AccessoryInventory;
import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class ItemNecklace extends ItemBase {

	public ItemNecklace(String name) {
		super(name);
		this.setMaxStackSize(1);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { CreativeTabs.MISC, ModTabs.ITEMS };
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack heldStack = playerIn.getHeldItem(handIn);

	    if (!worldIn.isRemote) {
	        AccessoryInventory accessoryInv = AccessoryInventory.fromPlayer(playerIn);
	        ItemStack slotStack = accessoryInv.getStackInSlot(0);

	        if (slotStack.isEmpty()) {
	            accessoryInv.setInventorySlotContents(0, heldStack.copy());
	            heldStack.setCount(0);
	            return new ActionResult<>(EnumActionResult.SUCCESS, ItemStack.EMPTY);
	        }
	    }

	    return new ActionResult<>(EnumActionResult.PASS, heldStack);
	}

}