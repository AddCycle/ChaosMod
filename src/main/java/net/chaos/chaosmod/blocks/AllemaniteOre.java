package net.chaos.chaosmod.blocks;

import java.io.IOException;
import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.tabs.ModTabs;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import util.guide.GenerateGuideContent;
import util.guide.model.block.GuideBlock;
import util.text.format.colors.ColorEnum;
import util.text.format.style.StyleEnum;

public class AllemaniteOre extends BlockBase {
	public String description;

	public AllemaniteOre(String name, Material material) {
		super(name, material);
		setCreativeTab(ModTabs.GENERAL_TAB);
		setHardness(5.0f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 3);
		setLightLevel(17);
		try {
			this.description = getBlockData().getDescription();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GuideBlock getBlockData() throws IOException {
		GenerateGuideContent util = new GenerateGuideContent();
		return util.getGuideBlock(util.getFileContent("allemanite_ore"));
	}

	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(ColorEnum.RED + "Mythical Ore");
		tooltip.add(ColorEnum.YELLOW + "From the deepest of the Nether");
		tooltip.add(StyleEnum.BOLD + "Can you do something out of it ?");
		tooltip.add("The following description is from the json file : ");
		tooltip.add(StyleEnum.ITALIC + "" + ColorEnum.GOLD + this.description);
	}
}