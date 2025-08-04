package net.chaos.chaosmod.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class EnderiteBlock extends BlockBase {

	public EnderiteBlock(String name, Material material) {
		super(name, material);
		setLightLevel(12);
		setHardness(5.0f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 3);
	}

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.RED + "Mythical Block");
		tooltip.add(TextFormatting.DARK_PURPLE + "" + TextFormatting.BOLD + "Are you out of you mind ?");
	}

}
