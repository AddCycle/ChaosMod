package net.chaos.chaosmod.jei.exploration;

import java.util.Arrays;
import java.util.List;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.item.ItemStack;

public class ExplorationMaker {
	public static List<ExplorationWrapper> getRecipes() {
        return Arrays.asList(
            new ExplorationWrapper(ItemStack.EMPTY, new ItemStack(ModItems.OXONIUM_INGOT))
        );
    }

}