package net.chaos.chaosmod.jei.forge_interface;

import java.util.Arrays;
import java.util.List;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ForgeInterfaceRecipeMaker {
	public static List<ForgeInterfaceRecipeWrapper> getRecipes() {
        return Arrays.asList(
            new ForgeInterfaceRecipeWrapper(
            		Arrays.asList(new ItemStack(ModItems.OXONIUM_INGOT), new ItemStack(ModItems.OXONIUM_INGOT), new ItemStack(ModItems.OXONIUM_INGOT)), new ItemStack(ModItems.OXONIUM_HALLEBERD)
            	),
            new ForgeInterfaceRecipeWrapper(
            		Arrays.asList(new ItemStack(ModItems.OXONIUM_INGOT, 3), new ItemStack(Items.FEATHER, 2), new ItemStack(Items.STRING, 3)), new ItemStack(ModItems.OXONIUM_BOW)
            	),
            new ForgeInterfaceRecipeWrapper(
            		Arrays.asList(new ItemStack(ModItems.OXONIUM_INGOT, 2), new ItemStack(ModItems.ALLEMANITE_INGOT, 2), new ItemStack(ModItems.ENDERITE_INGOT, 5)), new ItemStack(ModItems.ALL_IN_ONE_BOW)
            	),
            new ForgeInterfaceRecipeWrapper(
            		Arrays.asList(new ItemStack(ModItems.ALLEMANITE_INGOT, 3), new ItemStack(ModItems.OXONIUM_INGOT, 3), new ItemStack(ModItems.ENDERITE_INGOT, 7)), new ItemStack(ModItems.ALL_IN_ONE_SWORD)
            	)
            );
    }

}