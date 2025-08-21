package net.chaos.chaosmod.blocks.ores;

import java.util.List;

import net.chaos.chaosmod.blocks.BlockBase;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public abstract class AbstractOre extends BlockBase {

	public AbstractOre(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(5.0f);
		setResistance(15.0f);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		String fmt = String.format("%s[%s]%s %f%s",
			TextFormatting.RED,
			"MELTING POINT",
			TextFormatting.GRAY,
			this.getSmeltingTemperature(),
			"°C"
		);
		tooltip.add(fmt);
		super.addInformation(stack, player, tooltip, advanced);
	}
	
	public abstract float getSmeltingTemperature();

}
