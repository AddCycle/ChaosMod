package net.chaos.chaosmod.recipes.machine;

import java.util.Map;

import com.google.common.collect.Maps;

import net.chaos.chaosmod.recipes.OxoniumFurnaceRecipes;
import net.minecraft.item.ItemStack;

public class MachineRecipeRegistry {
	private static final MachineRecipeRegistry INSTANCE = new MachineRecipeRegistry();
    private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static MachineRecipeRegistry instance()
    {
        return INSTANCE;
    }
}
