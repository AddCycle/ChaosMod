package net.chaos.chaosmod.init;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.enchantments.EnchantmentLavaWalker;
import net.chaos.chaosmod.enchantments.EnchantmentVeinMiner;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantment.Rarity;

public class ModEnchants {
	public static final List<Enchantment> ENCHANTS = new ArrayList<>();

	public static final Enchantment VEIN_MINER = new EnchantmentVeinMiner("vein_miner", Rarity.COMMON);
	public static final Enchantment LAVA_WALKER = new EnchantmentLavaWalker("lava_walker", Rarity.COMMON);
}