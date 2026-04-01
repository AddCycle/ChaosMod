package net.chaos.chaosmod.enchantments.events;

import java.util.Map;

import net.chaos.chaosmod.enchantments.EnchantmentLavaWalker;
import net.chaos.chaosmod.init.ModEnchants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import util.Reference;
import util.blockstates.BlockHelper;

@EventBusSubscriber(modid = Reference.MODID)
public class EnchantmentsEventHandler {

	@SubscribeEvent
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
		EntityPlayer player = event.getPlayer();
		World world = event.getWorld();
		if (world.isRemote) return;

		ItemStack held = player.getHeldItemMainhand();

		BlockPos pos = event.getPos();
		IBlockState state = world.getBlockState(pos);
		
		System.out.println("condition: " + (held.isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(ModEnchants.VEIN_MINER, held) > 0));

		if (held.isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(ModEnchants.VEIN_MINER, held) > 0) {
			if (BlockHelper.isOreBlock(state)) {
				BlockHelper.progressiveDestroyConnectedBlocks(world, pos, state, player, held);
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		World world = player.getEntityWorld();
		if (world.isRemote) return;

		lavaWalk(player, world);
	}

	@SubscribeEvent
	/**
	 * Applying side-effects when an anvil gets used with ENCHANTED_BOOK
	 * 
	 * @param event
	 */
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();

		if (right.getItem() != Items.ENCHANTED_BOOK)
			return;

		Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(right);
		if (enchants.containsKey(ModEnchants.VEIN_MINER)) {
			int level = enchants.get(ModEnchants.VEIN_MINER);

			int xp = 30;
			event.setCost(xp);

			ItemStack output = left.copy();
			if (EnchantmentHelper.getEnchantmentLevel(ModEnchants.VEIN_MINER, output) == 0) {
				output.addEnchantment(ModEnchants.VEIN_MINER, level);
			}
			event.setOutput(output);
		}
	}

	private static void lavaWalk(EntityPlayer player, World world) {
		BlockPos pos = player.getPosition().down();
		Block block = player.world.getBlockState(pos).getBlock();

		if (block == Blocks.LAVA) {
			int i = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchants.LAVA_WALKER, player);

			if (i > 0) {
				EnchantmentLavaWalker.solidifyNearbyLava(player, world, pos.up(), i);
			}
		}
	}
}