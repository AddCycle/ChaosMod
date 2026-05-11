package net.chaos.chaosmod.items.upgrading;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.inventory.InventoryUpgrading;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

/**
 * FIXME : be careful to save the NBT of the stack in order to preserve enchants & durability
 */
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
    
    public static NonNullList<ItemStack> getRemainingItems(InventoryUpgrading inventory, World worldIn)
    {
        for (IUpgradingRecipe irecipe : REGISTRY)
        {
            if (irecipe.matches(inventory, worldIn))
            {
                return irecipe.getRemainingItems(inventory);
            }
        }

        NonNullList<ItemStack> nonnulllist = NonNullList.<ItemStack>withSize(inventory.getSizeInventory(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i)
        {
            nonnulllist.set(i, inventory.getStackInSlot(i));
        }

        return nonnulllist;
    }
    
    public static void register(SimpleUpgradingRecipe recipe) {
    	REGISTRY.add(recipe);
    }
}