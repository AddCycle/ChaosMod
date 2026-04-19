package net.chaos.chaosmod.jobs.events.miner;

import net.chaos.chaosmod.jobs.TargetType;
import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemSmeltedEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class JobMinerEventHandler {
	private static final String JOB_ID = Reference.PREFIX + "miner";

	@SubscribeEvent
	public static void onBlockMined(BlockEvent.BreakEvent event) {
		if (event.getWorld().isRemote) return;
		
		Block block = event.getState().getBlock();
		ResourceLocation id = block.getRegistryName();
		if (id == null)
			return;
		
		JobEventUtils.incrementRelatedMatchingTasks(id, TaskType.MINE, TargetType.BLOCK, JOB_ID, event.getPlayer());
	}
	
	@SubscribeEvent
	public static void onItemSmelt(ItemSmeltedEvent event) {
		Block block = Block.getBlockFromItem(event.smelting.getItem());
		if (block == null) return;

		ResourceLocation id = block.getRegistryName();
		if (id == null)
			return;
		
		JobEventUtils.incrementRelatedMatchingTasks(id, TaskType.SMELT, TargetType.BLOCK, JOB_ID, event.player);
	}

}