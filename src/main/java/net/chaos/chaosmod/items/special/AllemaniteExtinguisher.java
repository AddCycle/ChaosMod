package net.chaos.chaosmod.items.special;

import java.util.List;

import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class AllemaniteExtinguisher extends ItemBase {

	public AllemaniteExtinguisher(String name) {
		super(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(10);
		this.setNoRepair();
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(new TextComponentString("This thing is my safety belt...")
				.setStyle(new Style().setColor(TextFormatting.RED)).getFormattedText());
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote && player.isBurning()) {
			player.extinguish();
			player.getHeldItem(hand).damageItem(1, player);
			worldIn.setBlockToAir(pos.up());
		}

		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MISC, ModTabs.GENERAL_TAB };
    }
}
