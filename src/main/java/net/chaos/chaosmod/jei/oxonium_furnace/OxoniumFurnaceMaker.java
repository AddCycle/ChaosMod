package net.chaos.chaosmod.jei.oxonium_furnace;

import java.util.Arrays;
import java.util.List;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.item.ItemStack;

public class OxoniumFurnaceMaker {
	public static List<OxoniumFurnaceWrapper> getRecipes() {
        return Arrays.asList(
            new OxoniumFurnaceWrapper(new ItemStack(ModBlocks.OXONIUM_ORE), new ItemStack(ModItems.OXONIUM)),
            new OxoniumFurnaceWrapper(new ItemStack(ModBlocks.ALLEMANITE_ORE), new ItemStack(ModItems.ALLEMANITE_INGOT))
        );
    }

}