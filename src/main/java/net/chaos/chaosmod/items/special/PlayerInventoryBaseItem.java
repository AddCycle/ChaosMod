package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.backpack.inv.BackpackContainer;
import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import util.Reference;

public class PlayerInventoryBaseItem extends ItemBase {

	public PlayerInventoryBaseItem(String name) {
		super(name);
		setMaxStackSize(1);
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs() {
		return new CreativeTabs[] { ModTabs.GENERAL_TAB, CreativeTabs.TOOLS };
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack held = playerIn.getHeldItemMainhand();
		// playerIn.openGui(Main.instance, Reference.GUI_BACKPACK_ID, worldIn, (int) playerIn.posX, (int) playerIn.posY, (int) playerIn.posZ);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, held);
	}

}
