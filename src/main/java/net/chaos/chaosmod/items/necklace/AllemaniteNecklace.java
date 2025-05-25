package net.chaos.chaosmod.items.necklace;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AllemaniteNecklace extends ItemNecklace {

	public AllemaniteNecklace(String name) {
		super(name);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Apparently, this is going to help you a lot against a certain flying creature..."); // TODO : add localization
	}
}