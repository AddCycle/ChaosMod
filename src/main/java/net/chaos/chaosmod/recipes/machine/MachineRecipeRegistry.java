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
            new ItemStack(ModItems.OXONIUM_INGOT),
            new ItemStack(ModItems.OXONIUM_INGOT),
            new ItemStack(ModItems.OXONIUM_HALLEBERD)
        ));

        RECIPES.add(new ForgeRecipe(
            new ItemStack(ModItems.OXONIUM_INGOT, 3),
            new ItemStack(Items.FEATHER, 2),
            new ItemStack(Items.STRING, 3),
            new ItemStack(ModItems.OXONIUM_BOW)
        ));

        RECIPES.add(new ForgeRecipe(
            new ItemStack(ModItems.OXONIUM_INGOT, 3),
            new ItemStack(ModItems.ALLEMANITE_INGOT, 3),
            new ItemStack(ModItems.ENDERITE_INGOT, 4),
            new ItemStack(ModItems.ALL_IN_ONE_BOW)
        ));
        // Add more recipes here && don't forget to update the JEI recipes
    }
}
