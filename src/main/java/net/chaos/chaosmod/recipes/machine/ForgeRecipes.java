package net.chaos.chaosmod.recipes.machine;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Triple;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ForgeRecipes {
    private static final ForgeRecipes INSTANCE = new ForgeRecipes();
    private final List<Triple<Item, Item, Item>> inputs = new ArrayList<>();
    private final List<ItemStack> outputs = new ArrayList<>();

    public static ForgeRecipes getInstance() {
        return INSTANCE;
    }

    public void addRecipe(Item input1, Item input2, Item input3, ItemStack output) {
        inputs.add(Triple.of(input1, input2, input3));
        outputs.add(output);
    }

    public ItemStack getForgeResult(ItemStack i1, ItemStack i2, ItemStack i3) {
        for (int i = 0; i < inputs.size(); i++) {
            Triple<Item, Item, Item> triple = inputs.get(i);
            if (triple.getLeft() == i1.getItem() && triple.getMiddle() == i2.getItem() && triple.getRight() == i3.getItem()) {
                return outputs.get(i);
            }
        }
        return ItemStack.EMPTY;
    }
}