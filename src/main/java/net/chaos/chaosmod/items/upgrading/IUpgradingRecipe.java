package net.chaos.chaosmod.items.upgrading;

import net.chaos.chaosmod.inventory.InventoryUpgrading;
import net.minecraft.item.ItemStack;
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
}
