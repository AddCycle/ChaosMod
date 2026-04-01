package util.blockstates;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import net.chaos.chaosmod.world.events.tree.TreeTask;
import net.chaos.chaosmod.world.events.tree.TreeTaskManager;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import net.minecraftforge.oredict.OreDictionary;

public class BlockHelper {

	/**
	 * destroys the origin block and all the surroundings instantly (for progressive see method below)
	 * @param world
	 * @param origin
	 * @param targetState origin blockstate
	 * @param player
	 * @param stack itemstack used to break origin block
	 */
	public static void destroyConnectedBlocks(World world, BlockPos origin, IBlockState targetState, EntityPlayer player,
			ItemStack stack) {
		Queue<BlockPos> toCheck = new LinkedList<>();
		Set<BlockPos> visited = new HashSet<>();

		toCheck.add(origin);

		while (!toCheck.isEmpty()) {
			BlockPos pos = toCheck.poll();

			if (visited.contains(pos))
				continue;

			visited.add(pos);

			IBlockState state = world.getBlockState(pos);
			Block current = state.getBlock();
			Block target = targetState.getBlock();

			if (current == target) {
				Block block = state.getBlock();
				block.onBlockDestroyedByPlayer(world, pos, state);
				block.harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack);

				int silk_touch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack);
				int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
				int xp = block.getExpDrop(state, world, pos, fortune);
				if (xp > 0 && silk_touch == 0)
					block.dropXpOnBlockBreak(world, pos, xp);

				world.setBlockToAir(pos);

				for (EnumFacing facing : EnumFacing.values()) {
					BlockPos neighbor = pos.offset(facing);
					if (!visited.contains(neighbor)) {
						Block neighborBlock = world.getBlockState(neighbor).getBlock();
						if (neighborBlock == target) {
							toCheck.add(neighbor);
						}
					}
				}
			}
		}
	}

	public static void progressiveDestroyConnectedBlocks(World world, BlockPos origin, IBlockState targetState, EntityPlayer player,
			ItemStack stack) {
		Queue<BlockPos> toCheck = new LinkedList<>();
		Set<BlockPos> visited = new HashSet<>();
		Set<BlockPos> ores = new LinkedHashSet<>();

		toCheck.add(origin);

		while (!toCheck.isEmpty()) {
			BlockPos pos = toCheck.poll();

			if (visited.contains(pos))
				continue;

			visited.add(pos);

			IBlockState state = world.getBlockState(pos);
			Block current = state.getBlock();
			Block target = targetState.getBlock();

			if (current == target) {
				ores.add(pos);

				for (EnumFacing facing : EnumFacing.values()) {
					BlockPos neighbor = pos.offset(facing);
					if (!visited.contains(neighbor)) {
						Block neighborBlock = world.getBlockState(neighbor).getBlock();
						if (neighborBlock == target) {
							toCheck.add(neighbor);
						}
					}
				}
			}
		}

		TreeTask task = new TreeTask(new LinkedList<>(ores), player, stack);
		TreeTaskManager.addTask(world, task);
	}

	public static boolean isOreBlock(IBlockState state) {
		Block block = state.getBlock();

		block = normalizeOre(block);
		state = block.getDefaultState();

		Item item = Item.getItemFromBlock(block);
		if (item == Items.AIR) {
			return false;
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

		if (item.getRegistryName().getResourcePath().endsWith("_ore")) {
			return true;
		}

		return false;
	}

	public static Block normalizeOre(Block block) {
		if (block == Blocks.LIT_REDSTONE_ORE)
			return Blocks.REDSTONE_ORE;
		return block;
	}

	public static int[] getPosArray(BlockPos deathPos) {
		return new int[] {deathPos.getX(), deathPos.getY(), deathPos.getZ()};
	}
}