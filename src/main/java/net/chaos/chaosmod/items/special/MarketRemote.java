package net.chaos.chaosmod.items.special;

import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.market.MarketGuiHelper;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class MarketRemote extends ItemBase {

	public MarketRemote(String name) {
		super(name);
		setMaxStackSize(1);
		setMaxDamage(-1); // unbreakable
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!worldIn.isRemote) {
	        EntityPlayerMP playerMP = (EntityPlayerMP) playerIn;
	        playerMP.sendMessage(new TextComponentString("right-click is processed"));
	        MarketGuiHelper.openMarketGui(playerMP, worldIn);
	    }
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MATERIALS, ModTabs.ITEMS, ModTabs.SEARCH };
    }
}