package net.chaos.chaosmod.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import util.text.format.colors.ColorEnum;
import util.text.format.style.StyleEnum;

public class EnderiteOre extends BlockBase {

	public EnderiteOre(String name, Material material) {
		super(name, material);
		setCreativeTab(ModTabs.GENERAL_TAB);
		setHardness(5.0f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 3);
		setLightLevel(17);
	}

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ColorEnum.RED + "Mythical Ore");
		tooltip.add(ColorEnum.DARK_PURPLE + "" + StyleEnum.ITALIC + "This mineral will be useful for teleporting purposes");
	}
}
