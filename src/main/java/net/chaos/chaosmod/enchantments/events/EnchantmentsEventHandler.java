package net.chaos.chaosmod.enchantments.events;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import net.chaos.chaosmod.init.ModEnchants;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@EventBusSubscriber
public class EnchantmentsEventHandler {
	
	@SubscribeEvent
	// VEIN_MINER
	public static void onBlockBreak(BlockEvent.BreakEvent event) {
	    EntityPlayer player = event.getPlayer();
	    World world = event.getWorld();
	    ItemStack held = player.getHeldItemMainhand();
	    
	    // Block infos
	    BlockPos pos = event.getPos();
	    IBlockState state = world.getBlockState(pos);

	    if (held.isItemEnchanted() && EnchantmentHelper.getEnchantmentLevel(ModEnchants.VEIN_MINER, held) > 0) {
		if (!world.isRemote) {
			if (isOreBlock(state)) {
				veinMine(world, pos, state, player, held);
			}
		}
	    }
	}
	
	@SubscribeEvent
	// TWEAKING ANVIL APPLY OF ENCHANTED BOOKS TO ITEMS
	public static void onAnvilUpdate(AnvilUpdateEvent event) {
	    ItemStack left = event.getLeft();   // the item in the left slot
	    ItemStack right = event.getRight(); // the item in the right slot

	    // If right is an enchanted book with your custom enchant
	    if (right.getItem() == Items.ENCHANTED_BOOK) {
	        Map<Enchantment, Integer> enchants = EnchantmentHelper.getEnchantments(right);
	        if (enchants.containsKey(ModEnchants.VEIN_MINER)) {
	            int level = enchants.get(ModEnchants.VEIN_MINER);

	            // Force a custom cost (e.g. always 30 levels)
	            event.setCost(30);

	            // You can also define what the output should be
	            ItemStack output = left.copy();
	            if (EnchantmentHelper.getEnchantmentLevel(ModEnchants.VEIN_MINER, output) == 0) {
	                output.addEnchantment(ModEnchants.VEIN_MINER, level);
	            }
	            event.setOutput(output);
	        }
	    }
	}

	private static boolean isOreBlock(IBlockState state) {
		Block block = state.getBlock();

		// Normalize special vanilla cases
		if (block == Blocks.LIT_REDSTONE_ORE) {
			block = Blocks.REDSTONE_ORE;
			state = normalizeOre(block).getDefaultState();
		}

		// If the block itself is explicitly one of the known vanilla ores
		if (block == Blocks.COAL_ORE ||
				block == Blocks.IRON_ORE ||
				block == Blocks.GOLD_ORE ||
				block == Blocks.DIAMOND_ORE ||
				block == Blocks.EMERALD_ORE ||
				block == Blocks.LAPIS_ORE ||
				block == Blocks.QUARTZ_ORE ||
				block == Blocks.REDSTONE_ORE) {
			return true;
		}

		// Otherwise, check OreDictionary (modded ores usually register here)
		Item item = Item.getItemFromBlock(block);
		if (item == Items.AIR) {
			return false; // no item form, can't be ore
		}

		ItemStack stack = new ItemStack(item, 1, block.damageDropped(state));
		if (stack.isEmpty()) {
			return false;
		}

		for (int id : OreDictionary.getOreIDs(stack)) {
			String name = OreDictionary.getOreName(id);
			if (name != null && name.toLowerCase().startsWith("ore")) {
				return true;
			}
		}

		return false;
	}

	private static void veinMine(World world, BlockPos origin, IBlockState targetState, EntityPlayer player, ItemStack tool) {
		Queue<BlockPos> toCheck = new LinkedList<>();
		Set<BlockPos> visited = new HashSet<>();

		toCheck.add(origin);

		while (!toCheck.isEmpty()) {
			BlockPos pos = toCheck.poll();

			if (visited.contains(pos)) continue;
			visited.add(pos);

			IBlockState state = world.getBlockState(pos);
			// NEW: normalize before comparing
			Block current = normalizeOre(state.getBlock());
			Block target = normalizeOre(targetState.getBlock());

			if (current == target) { // instead of state.getBlock() == targetState.getBlock()
				// Break this block like normal
				Block block = state.getBlock();
				block.onBlockDestroyedByPlayer(world, pos, state);
				block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), tool);

				int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
				int xp = block.getExpDrop(state, world, pos, fortune);
				if (xp > 0) block.dropXpOnBlockBreak(world, pos, xp);

				world.setBlockToAir(pos);

				// Check neighbors
				for (EnumFacing face : EnumFacing.values()) {
					BlockPos neighbor = pos.offset(face);
					if (!visited.contains(neighbor)) {
						Block neighborBlock = normalizeOre(world.getBlockState(neighbor).getBlock());
						if (neighborBlock == target) { // instead of == targetState.getBlock()
							toCheck.add(neighbor);
						}
					}
				}
			}
		}
	}

	private static Block normalizeOre(Block block) {
		if (block == Blocks.LIT_REDSTONE_ORE) return Blocks.REDSTONE_ORE;
		return block;
	}
}
