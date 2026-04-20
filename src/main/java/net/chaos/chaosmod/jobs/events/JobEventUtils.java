package net.chaos.chaosmod.jobs.events;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.jobs.TargetType;
import net.chaos.chaosmod.jobs.data.SharedTaskProgressData;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.chaos.chaosmod.jobs.task.SharedTaskProgress;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

public class JobEventUtils {

	/**
	 * The system is done a way that the task progress is incremented by amount, capping at goal and syncs capability jobs
	 * example : incrementTask(player, "chaosmod:tamer", "chaosmod:tame_wolf", amount);
	 * @param player
	 * @param jobid
	 * @param taskId
	 */
	public static void incrementTask(EntityPlayerMP player, String jobid, String taskId, int amount) {
		String jobId = jobid;

		World world = player.getServerWorld();
		MinecraftServer server = player.getServer();

		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs == null) {
			Main.getLogger().info("jobs capability is null never incrementing shared task");
			return;
		}
		
		JobTask task = getTask(jobId, taskId);
		if (task == null) {
			Main.getLogger().info("task is null never incrementing shared task");
			return;
		}

		if (task.shared) {
		    SharedTaskProgress sharedProgress = SharedTaskProgress.get(world);
		    if (sharedProgress != null) {
		        sharedProgress.incrementTask(server, player.getServerWorld(), jobId, taskId, amount);
		        // marks WorldSavedData dirty so it saves
		        world.getMapStorage().getOrLoadData(SharedTaskProgressData.class, SharedTaskProgressData.NAME).markDirty();
		    }
		} else {
			jobs.getProgress(jobId).incrementTask(player, jobId, taskId, amount);
		}

		Main.getLogger().info("Job done, incrementing task: [{}:{}]", jobid, taskId);
	}
	
	public static JobTask getTask(String jobId, String taskId) {
		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
		return task;
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

	/**
	 * If the target == id, then increment task
	 * @param id			registryName
	 * @param taskType
	 * @param targetType
	 * @param jobId			ex: "chaosmod:tamer"
	 * @param player
	 */
	public static void incrementRelatedMatchingTasks(ResourceLocation id, TaskType taskType, TargetType targetType, String jobId, EntityPlayer player) {
		incrementRelatedMatchingTasksByCount(id, taskType, targetType, jobId, player, 1);
	}

	/**
	 * If the target == id, then increment task
	 * @param id			registryName
	 * @param taskType
	 * @param targetType
	 * @param jobId			ex: "chaosmod:tamer"
	 * @param player
	 * @param amount
	 */
	public static void incrementRelatedMatchingTasksByCount(ResourceLocation id, TaskType taskType, TargetType targetType, String jobId, EntityPlayer player, int amount) {
		if (player.getEntityWorld().isRemote) return;

		Collection<JobTask> tasks = JobsManager.TASK_MANAGER.getTasks(jobId);
		for (JobTask task : tasks) {
			if (task == null) {
				continue;
			}
			if (task.target == null) {
				continue;
			}

			if (task.type != taskType || task.target.type != targetType)
				continue;

			if (id.toString().equals(task.target.target)) {
				incrementTask((EntityPlayerMP) player, jobId, task.id, amount);
			}
		}
	}

	public static String prefixId(String id) {
		return Reference.PREFIX + id;
	}
}