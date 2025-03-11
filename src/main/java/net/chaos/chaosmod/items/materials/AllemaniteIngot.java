package net.chaos.chaosmod.items.materials;

import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.items.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import util.Reference;
import util.guide.model.block.GuideBlock;

public class AllemaniteIngot extends ItemBase {

	public AllemaniteIngot(String name) {
		super(name);
	}
	
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("\u00A7cMythical material");
		tooltip.add("\u00a7eFrom the deepest of the Nether");
		tooltip.add("\u00A7lCan you do something out of it ?");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);

		if(hand == EnumHand.MAIN_HAND)
		{
			player.openGui(Main.instance, Reference.GUI_GUIDE_ID, worldIn, 0, 0, 0);
		}

		return new ActionResult<ItemStack>(EnumActionResult.PASS, stack);
	}
}
