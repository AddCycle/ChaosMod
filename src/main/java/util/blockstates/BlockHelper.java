package util.blockstates;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockHelper {

	/**
	 * destroys the origin block and all the surroundings
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

				int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, stack);
				int xp = block.getExpDrop(state, world, pos, fortune);
				if (xp > 0)
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

	public static int[] getPosArray(BlockPos deathPos) {
		return new int[] {deathPos.getX(), deathPos.getY(), deathPos.getZ()};
	}
}