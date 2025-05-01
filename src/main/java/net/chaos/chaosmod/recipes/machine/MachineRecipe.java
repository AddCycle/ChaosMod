package net.chaos.chaosmod.recipes.machine;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class MachineRecipe {
	public final Ingredient[] inputs;
	public final ItemStack output;
	public final int fabricationTime;

	public MachineRecipe(Ingredient[] inputs, ItemStack output, int fabricationTime) {
		this.inputs = inputs;
		this.output = output;
		this.fabricationTime = fabricationTime;
	}
	
	public boolean matches(ItemStack... stack) {
		for (int i = 0; i < stack.length; i++) if (!inputs[i].apply(stack[i])) return false;
		return true;
	}

}