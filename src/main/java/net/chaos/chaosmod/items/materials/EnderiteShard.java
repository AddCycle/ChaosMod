package net.chaos.chaosmod.items.materials;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.items.ItemBase;
import net.chaos.chaosmod.tabs.ModTabs;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.text.format.colors.ColorEnum;
import util.text.format.style.StyleEnum;

public class EnderiteShard extends ItemBase {

	public EnderiteShard(String name) {
		super(name);
		this.setMaxDamage(1);
	}

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ColorEnum.DARK_PURPLE + "" + StyleEnum.BOLD + "I ain't that strong tho");
    }
	
	// on check la zone en 3x3
	private boolean is_correct_setup(BlockPos pos, World worldIn) {
		BlockPos under_layer = pos.down();
		BlockPos[] to_verify = 
			{
				under_layer, under_layer.north(), under_layer.south(), under_layer.east(), under_layer.west(),
				under_layer.north().east(), under_layer.north().west(),
				under_layer.south().east(), under_layer.south().west(),
				under_layer.east().north(), under_layer.east().south(),
				under_layer.west().north(), under_layer.west().south()
			};
		for (BlockPos p : to_verify) {
			if (!Block.isEqualTo(worldIn.getBlockState(p).getBlock(), ModBlocks.ALLEMANITE_BLOCK)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		TileEntity te = worldIn.getTileEntity(pos);
		player.getHeldItemMainhand().damageItem(2, player);
		if (te instanceof TileEntityOxoniumFurnace) {
			if (is_correct_setup(pos, worldIn)) {
				worldIn.setBlockToAir(pos);
				worldIn.setBlockState(pos, ModBlocks.OXONIUM_FURNACE.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, 0, player, hand));
				if (worldIn.isRemote)
					player.sendMessage(new TextComponentString("Are you sure you wanna do this ?").setStyle(new Style().setColor(TextFormatting.GOLD).setBold(true)));
			} else {
				if (worldIn.isRemote)
					player.sendMessage(new TextComponentString("Hum I'm not sure about the setup...").setStyle(new Style().setColor(TextFormatting.RED).setItalic(true)));
			}
		}
		return EnumActionResult.SUCCESS;
	}
	
	@Override
	public CreativeTabs[] getCreativeTabs()
    {
        return new CreativeTabs[]{ CreativeTabs.MATERIALS, ModTabs.GENERAL_TAB }; // You can add other tabs
    }

}
