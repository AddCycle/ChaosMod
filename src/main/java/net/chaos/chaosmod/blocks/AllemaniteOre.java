package net.chaos.chaosmod.blocks;

import java.io.IOException;
import java.lang.reflect.Field;
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
	private String description;
	private String[] screenshots;

	public AllemaniteOre(String name, Material material) {
		super(name, material);
		setCreativeTab(ModTabs.GENERAL_TAB);
		setHardness(5.0f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 3);
		setLightLevel(17);
		try {
			this.description = getBlockData().getDescription();
			this.screenshots = getBlockData().getScreenshots();
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
		tooltip.add(ColorEnum.GOLD + "" + StyleEnum.ITALIC + this.description);
		tooltip.add("The following screenshots paths are from the json file : ");
		tooltip.add(ColorEnum.GOLD + "screen[0]: " + StyleEnum.ITALIC + this.screenshots[0]);
		tooltip.add(ColorEnum.GOLD + "screen[1]: " + StyleEnum.ITALIC + this.screenshots[1]);
	}
}