package net.chaos.chaosmod.world.events.tree;

import java.util.Queue;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class TreeTask {
	Queue<BlockPos> logs;
	EntityPlayer player;
	ItemStack tool;
	int tickDelay = 2;
	int counter = 0;

	public TreeTask(Queue<BlockPos> logs, EntityPlayer player, ItemStack tool) {
		this.logs = logs;
		this.player = player;
		this.tool = tool;
	}
}
