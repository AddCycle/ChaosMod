package net.chaos.chaosmod.items.upgrading;

import net.chaos.chaosmod.inventory.InventoryUpgrading;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public interface IUpgradingRecipe {

    /**
     * Used to check if a recipe matches current upgrading inventory
     */
    boolean matches(InventoryUpgrading inv, World worldIn);

    /**
     * Returns an Item that is the result of this recipe
     */
    ItemStack getUpgradingResult(InventoryUpgrading inv);
    
    default NonNullList<ItemStack> getRemainingItems(InventoryUpgrading inv)
    {
        NonNullList<ItemStack> ret = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < ret.size(); i++)
        {
            ret.set(i, UpgradingHelper.getContainerItem(inv.getStackInSlot(i)));
        }
        return ret;
    }
}