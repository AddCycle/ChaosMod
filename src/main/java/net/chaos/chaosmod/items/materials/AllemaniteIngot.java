package net.chaos.chaosmod.items.materials;

import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.items.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class AllemaniteIngot extends ItemBase {

	public AllemaniteIngot(String name) {
		super(name);
	}
	
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TextFormatting.RED + "Mythical material");
		tooltip.add(TextFormatting.YELLOW + "From the deepest of the Nether");
		tooltip.add(TextFormatting.BOLD + "Can you do something out of it ?");
	}
}