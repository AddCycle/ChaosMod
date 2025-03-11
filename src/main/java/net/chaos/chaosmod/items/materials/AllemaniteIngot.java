package net.chaos.chaosmod.items.materials;

import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.items.ItemBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import util.guide.model.block.GuideBlock;

public class AllemaniteIngot extends ItemBase {

	public AllemaniteIngot(String name) {
		super(name);
	}
	
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("\u00A7cMythical material");
		tooltip.add("\u00a7eFrom the deepest of the Nether");
		tooltip.add("\u00A7lCan you do something out of it ?");
	}
}
