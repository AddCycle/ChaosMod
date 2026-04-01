package net.chaos.chaosmod.world.events.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import util.Reference;
import util.blockstates.BlockHelper;

@EventBusSubscriber(modid = Reference.MODID)
public class TreeTaskManager {
	private static final List<TreeTask> tasks = new ArrayList<>();

	public static void addTask(World world, TreeTask task) {
		tasks.add(task);
	}

	@SubscribeEvent
	public static void onTick(TickEvent.WorldTickEvent event) {
		if (event.phase != TickEvent.Phase.END || event.world.isRemote)
			return;

		Iterator<TreeTask> it = tasks.iterator();

		while (it.hasNext()) {
			TreeTask task = it.next();

			task.counter++;

			if (task.counter >= task.tickDelay) {
				BlockPos pos = task.logs.poll();

				if (pos != null) {
					IBlockState state = event.world.getBlockState(pos);
					Block block = state.getBlock();
					ItemStack tool = task.tool.copy();

					if (event.world.isAirBlock(pos))
						continue;

					block.onBlockDestroyedByPlayer(event.world, pos, state);
					block.harvestBlock(event.world, task.player, pos, state, event.world.getTileEntity(pos), tool);

					if (BlockHelper.isOreBlock(state)) {
						int silk_touch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, tool);
						int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
						int xp = block.getExpDrop(state, event.world, pos, fortune);
						if (xp > 0 && silk_touch == 0)
							block.dropXpOnBlockBreak(event.world, pos, xp);
					}

					event.world.setBlockToAir(pos);
				}

				task.counter = 0;
			}

			if (task.logs.isEmpty()) {
				it.remove();
			}
		}
	}
}