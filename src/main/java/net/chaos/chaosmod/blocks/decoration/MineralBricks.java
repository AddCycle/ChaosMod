package net.chaos.chaosmod.blocks.decoration;

import java.util.List;

import net.chaos.chaosmod.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class MineralBricks extends BlockBase {
	public TextFormatting format;
	public String description;

	public MineralBricks(String name, Material material, TextFormatting color, String description) {
		super(name, material);
		setHardness(2.0F);
		setResistance(3000.0f);
		setSoundType(SoundType.STONE);
		this.format = color;
		this.description = description;
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add(new TextComponentString(description).setStyle(new Style().setUnderlined(true).setColor(format)).getFormattedText());
	}

}
