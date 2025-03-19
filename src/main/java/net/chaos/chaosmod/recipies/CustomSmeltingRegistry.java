package net.chaos.chaosmod.recipies;

import javax.annotation.Nonnull;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CustomSmeltingRegistry extends GameRegistry {

	public static void addSmelting(Block input, @Nonnull ItemStack output, float xp)
    {
        OxoniumFurnaceRecipes.instance().addSmeltingRecipeForBlock(input, output, xp);
    }

	public static void addSmelting(Item input, @Nonnull ItemStack output, float xp)
    {
        OxoniumFurnaceRecipes.instance().addSmelting(input, output, xp);
    }

	public static void addSmelting(@Nonnull ItemStack input, @Nonnull ItemStack output, float xp)
    {
        OxoniumFurnaceRecipes.instance().addSmeltingRecipe(input, output, xp);
    }
}
