package net.chaos.chaosmod.items.upgrading;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

public class UpgradingHelper {

    @Nonnull
    public static ItemStack getContainerItem(@Nonnull ItemStack stack)
    {
        if (stack.getItem().hasContainerItem(stack))
        {
            stack = stack.getItem().getContainerItem(stack);
            if (!stack.isEmpty())
            {
                return ItemStack.EMPTY;
            }
            return stack;
        }
        return ItemStack.EMPTY;
    }

}