package net.chaos.chaosmod.items.upgrading;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.inventory.InventoryUpgrading;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class UpgradingManager {
	private static final List<IUpgradingRecipe> REGISTRY = Lists.newArrayList();
	
	// FIXME : make a delegate class to register recipes on mod init
	static {
		register(new SimpleUpgradingRecipe(
			new ItemStack(Items.DIAMOND_CHESTPLATE),
			new ItemStack(ModItems.OXONIUM_INGOT),
			new ItemStack(ModItems.OXONIUM_CHESTPLATE))
		);
	}

    @Nullable
    public static IUpgradingRecipe findMatchingRecipe(InventoryUpgrading inventory, World worldIn)
    {
        for (IUpgradingRecipe irecipe : REGISTRY)
        {
            if (irecipe.matches(inventory, worldIn))
            {
                return irecipe;
            }
        }

        return null;
    }
    
    public static void register(SimpleUpgradingRecipe recipe) {
    	REGISTRY.add(recipe);
    }
}