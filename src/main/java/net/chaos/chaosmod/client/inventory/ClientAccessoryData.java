package net.chaos.chaosmod.client.inventory;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ClientAccessoryData {
	private static final Map<EntityPlayer, ItemStack> necklaceMap = new WeakHashMap<>();

    public static void setPlayerNecklace(EntityPlayer player, ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            necklaceMap.remove(player);
        } else {
            necklaceMap.put(player, stack);
        }
    }

    public static ItemStack getPlayerNecklace(EntityPlayer player) {
        return necklaceMap.getOrDefault(player, ItemStack.EMPTY);
    }

}
