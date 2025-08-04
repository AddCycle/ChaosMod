package net.chaos.chaosmod.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class EnderiteOre extends BlockBase {

	public EnderiteOre(String name, Material material) {
		super(name, material);
		setHardness(5.0f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 4);
		setLightLevel(17);
	}

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.RED + "Mythical Ore");
		tooltip.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.ITALIC + "This mineral will be useful for teleporting purposes");
	}
	
	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune) {
		return 1;
	}

	@Override
	public void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {
		super.dropXpOnBlockBreak(worldIn, pos, amount);
	}
}