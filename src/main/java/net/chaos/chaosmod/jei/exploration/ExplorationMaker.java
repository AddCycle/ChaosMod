package net.chaos.chaosmod.jei.exploration;

import java.util.Arrays;
import java.util.List;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.jei.oxonium_furnace.OxoniumFurnaceWrapper;
import net.minecraft.item.ItemStack;

public class ExplorationMaker {
	public static List<ExplorationWrapper> getRecipes() {
        return Arrays.asList(
            new ExplorationWrapper(new ItemStack(ModBlocks.OXONIUM_ORE), new ItemStack(ModItems.OXONIUM_INGOT))
        );
    }

}