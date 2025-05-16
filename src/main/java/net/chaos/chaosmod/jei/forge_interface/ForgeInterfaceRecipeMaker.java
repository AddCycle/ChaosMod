package net.chaos.chaosmod.jei.forge_interface;

import java.util.Arrays;
import java.util.List;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.item.ItemStack;

public class ForgeInterfaceRecipeMaker {
	public static List<ForgeInterfaceRecipeWrapper> getRecipes() {
        return Arrays.asList(
            new ForgeInterfaceRecipeWrapper(Arrays.asList(new ItemStack(ModBlocks.OXONIUM_ORE), new ItemStack(ModItems.OXONIUM_INGOT), new ItemStack(ModItems.OXONIUM_INGOT)), new ItemStack(ModItems.OXONIUM_HALLEBERD))
        );
    }

}