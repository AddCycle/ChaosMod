package net.chaos.chaosmod.items.food;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class MineralFood extends FoodEffectBase {
	private String description;

	public MineralFood(String name, int amount, float saturation, Block crops, Block soil, PotionEffect effect, String description) {
		super(name, amount, saturation, crops, soil, effect);
		this.description = description;
	}

	public MineralFood(String name, int amount, float saturation, Block crops, Block soil, PotionEffect effect) {
		this(name, amount, saturation, crops, soil, effect, "");
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (!this.description.isEmpty()) tooltip.add(this.description);
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

}
