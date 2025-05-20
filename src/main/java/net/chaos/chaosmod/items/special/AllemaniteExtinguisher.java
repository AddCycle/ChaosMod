package net.chaos.chaosmod.items.special;

import java.util.List;

import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
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
		tooltip.add(new TextComponentString("This thing is my safety belt...") // TODO : add localization
				.setStyle(new Style().setColor(TextFormatting.RED)).getFormattedText());
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		BlockPos playerFeet = new BlockPos(player.posX, player.posY - 0.1, player.posZ);
	    ItemStack heldItem = player.getHeldItem(hand);

	    if (player.isBurning() && pos.equals(playerFeet)) {
	        if (!worldIn.isRemote) {
	            player.extinguish();
	            heldItem.damageItem(1, player);

	            BlockPos above = pos.up();
	            if (worldIn.getBlockState(above).getBlock().isFireSource(worldIn, above, facing)) {
	                worldIn.setBlockToAir(above);
	            }

	            return EnumActionResult.SUCCESS;
	        } else {
				double R = 0xFFFFFF;
				double G = 0xFFFFFF;
				double B = 0xFFFFFF;
				worldIn.spawnParticle(EnumParticleTypes.REDSTONE, true, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, R, G, B);
	            return EnumActionResult.SUCCESS;
	        }
	    }

	    return EnumActionResult.FAIL;
	}

	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MISC, ModTabs.ITEMS };
    }
}
