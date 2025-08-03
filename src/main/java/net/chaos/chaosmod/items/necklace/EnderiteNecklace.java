package net.chaos.chaosmod.items.necklace;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

// FIXME : fruit punch from Mathsmod must be counter only by jarate as soon as it is implemented
public class EnderiteNecklace extends ItemNecklace {

	public EnderiteNecklace(String name) {
		super(name);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		String info = new TextComponentTranslation("Can prevent wither effect & clowny jokes").getFormattedText();
		tooltip.add(info);
	}

}
