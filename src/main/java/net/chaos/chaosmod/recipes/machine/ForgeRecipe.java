package net.chaos.chaosmod.recipes.machine;

import net.minecraft.item.ItemStack;

public class ForgeRecipe {
	public final ItemStack input1;
    public final ItemStack input2;
    public final ItemStack input3;
    public final ItemStack output;

    public ForgeRecipe(ItemStack in1, ItemStack in2, ItemStack in3, ItemStack out) {
        this.input1 = in1;
        this.input2 = in2;
        this.input3 = in3;
        this.output = out;
    }

    public boolean matches(ItemStack slot0, ItemStack slot1, ItemStack slot2) {
        return ItemStack.areItemsEqual(input1, slot0)
            && ItemStack.areItemsEqual(input2, slot1)
            && ItemStack.areItemsEqual(input3, slot2);
    }
}