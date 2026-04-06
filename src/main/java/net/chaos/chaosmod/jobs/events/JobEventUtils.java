package net.chaos.chaosmod.jobs.events;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

public class JobEventUtils {

	/**
	 * The system is done a way that the task progress is incremented by amount, capping at goal and syncs capability jobs
	 * @param player
	 * @param jobid
	 * @param taskId
	 */
	public static void incrementTask(EntityPlayerMP player, String jobid, String taskId, int amount) {
		String jobId = prefixId(jobid);

		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs == null)
			return;

		jobs.getProgress(jobId).incrementTask(player, jobId, prefixId(taskId), amount);

		Main.getLogger().info("Job done, incrementing task: [{}:{}]", jobid, taskId);
	}

	/**
	 * The system is done a way that the task progress is incremented by 1, capping at goal and syncs capability jobs
	 * @param player
	 * @param jobid
	 * @param taskId
	 */
	public static void incrementTask(EntityPlayerMP player, String jobid, String taskId) {
		incrementTask(player, jobid, taskId, 1);
	}

	/**
	 * Gets the player level in the jobId for instance : getPlayerLevel(player, "farmer");
	 * @param player
	 * @param jobid
	 * @return -1 on error
	 */
	public static int getPlayerLevel(EntityPlayerMP player, String jobid) {
		String jobId = prefixId(jobid);

		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs == null)
			return -1;

		int level = jobs.getProgress(jobId).getLevel();
		return level;
	}

	public static void onHarvestBlock(BlockEvent.BreakEvent event, ResourceLocation blockId,
			Consumer<Block> callback) {
		Block block = ForgeRegistries.BLOCKS.getValue(blockId);
		if (block == null || block != event.getState().getBlock())
			return;

		callback.accept(block);
	}

	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event, ResourceLocation blockId,
			Consumer<Block> callback) {
		if (event.getWorld().isRemote)
			return;

		Block block = ForgeRegistries.BLOCKS.getValue(blockId);
		if (block == null || block != event.getWorld().getBlockState(event.getPos()).getBlock())
			return;

		callback.accept(block);
	}

	/**
	 * Safely comparing registryNames instead of @Nullable items
	 * @param event
	 * @param rl
	 * @param callback
	 */
	public static void onItemFished(ItemFishedEvent event, ResourceLocation rl, BiConsumer<List<ItemStack>, Integer> callback) {
		onItemFished(event, stack -> stack.getItem().getRegistryName().equals(rl), callback);
	}

	public static void onItemFished(ItemFishedEvent event, Predicate<ItemStack> predicate, BiConsumer<List<ItemStack>, Integer> callback) {
		int count = (int) event.getDrops().stream().filter(predicate).count();
		if (count > 0)
			callback.accept(event.getDrops(), count);
	}

	public static String prefixId(String id) {
		return Reference.PREFIX + id;
	}

}