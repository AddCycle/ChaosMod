package net.chaos.chaosmod.client.inventory;

import java.util.Map;
import java.util.WeakHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ClientAccessoryData {
	private static final Map<EntityPlayer, ItemStack> necklaceMap = new WeakHashMap<>();
	private static final Map<EntityPlayer, ItemStack> shieldMap = new WeakHashMap<>();
	private static final Map<EntityPlayer, ItemStack> charmMap = new WeakHashMap<>();

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

    public static void setPlayerShield(EntityPlayer player, ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            shieldMap.remove(player);
        } else {
            shieldMap.put(player, stack);
        }
    }

    public static ItemStack getPlayerShield(EntityPlayer player) {
        return shieldMap.getOrDefault(player, ItemStack.EMPTY);
    }

    public static void setPlayerCharm(EntityPlayer player, ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            charmMap.remove(player);
        } else {
            charmMap.put(player, stack);
        }
    }

    public static ItemStack getPlayerCharm(EntityPlayer player) {
        return charmMap.getOrDefault(player, ItemStack.EMPTY);
    }
}
