package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class CraftingWand extends ItemBase {

	public CraftingWand(String name) {
		super(name);
		setMaxStackSize(1);
		setMaxDamage(-1); // unbreakable
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
			playerIn.displayGui(new BlockWorkbench.InterfaceCraftingTable(worldIn, playerIn.getPosition()) {
				@Override
				public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
					return new ContainerWorkbench(playerInventory, worldIn, playerIn.getPosition()) {
						@Override
						public boolean canInteractWith(EntityPlayer playerIn) {
							return true;
						}
					};
				}
			});
		}
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MATERIALS, ModTabs.ITEMS, ModTabs.SEARCH };
    }
}