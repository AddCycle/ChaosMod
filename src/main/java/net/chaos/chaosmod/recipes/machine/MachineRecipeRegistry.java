package net.chaos.chaosmod.recipes.machine;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MachineRecipeRegistry {
	public static final List<ForgeRecipe> RECIPES = new ArrayList<>();

    public static void init() {
        RECIPES.add(new ForgeRecipe(
            new ItemStack(ModItems.OXONIUM_INGOT),
            new ItemStack(ModItems.ALLEMANITE_INGOT),
            new ItemStack(ModItems.ENDERITE_INGOT),
            new ItemStack(ModItems.OXONIUM_HALLEBERD)
        ));

        RECIPES.add(new ForgeRecipe(
            new ItemStack(ModItems.OXONIUM_INGOT),
            ItemStack.EMPTY,
            new ItemStack(Items.STRING, 3),
            new ItemStack(ModItems.OXONIUM_BOW)
        ));
        // Add more recipes here
    }
}
