package net.chaos.chaosmod.jobs.task;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.init.ModSounds;
import net.chaos.chaosmod.jobs.JobProgress;
import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.jobs.data.SharedTaskProgressData;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketSyncSharedTasks;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;

public class SharedTaskProgress {
	private final Map<String, Map<String, Integer>> progress = new HashMap<>(); // jobId -> taskId -> current progress
	private static final Gson GSON = new Gson();

	public static SharedTaskProgress get(World world) {
		if (world.isRemote) return null;

		MapStorage storage = world.getMapStorage();
		SharedTaskProgressData data = (SharedTaskProgressData) storage.getOrLoadData(SharedTaskProgressData.class,
				SharedTaskProgressData.NAME);
		if (data == null) {
			data = new SharedTaskProgressData();
			storage.setData(SharedTaskProgressData.NAME, data);
		}
		return data.getProgress();
	}

	public int getTaskProgress(String jobId, String taskId) {
		return progress.getOrDefault(jobId, Collections.emptyMap()).getOrDefault(taskId, 0);
	}

	public void incrementTask(MinecraftServer server, World world, String jobId, String taskId, int amount) {
		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
		if (task == null)
			return;

		Map<String, Integer> jobProgress = progress.computeIfAbsent(jobId, k -> new HashMap<>());
		int before = jobProgress.getOrDefault(taskId, 0);
		int after = Math.min(before + amount, task.goal);
		jobProgress.put(taskId, after);
		
		MapStorage storage = world.getMapStorage();
	    SharedTaskProgressData data = (SharedTaskProgressData) storage.getOrLoadData(
	        SharedTaskProgressData.class, SharedTaskProgressData.NAME);
	    if (data != null) data.markDirty();

		// Broadcast to all players
		syncToAll(server, this);

		if (before < task.goal && after >= task.goal) {
			onSharedTaskComplete(server, jobId, taskId);
		}
	}

	private void onSharedTaskComplete(MinecraftServer server, String jobId, String taskId) {
		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);

		// FIXME : make a system in the interface to click a button to redeem the common
		// task rewards
		// Reward every online player (you see the issue if the player is offline, no
		// rewards)
		for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
			PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
			if (jobs == null) continue;

			JobProgress jobProgress = jobs.getProgress(jobId);
			if (jobProgress != null) {
				jobProgress.addExp(player, jobId, task.rewardExp);
			}
			player.getEntityWorld().playSound(null, player.getPosition(), ModSounds.JOBTASK_COMPLETE,
					SoundCategory.NEUTRAL, 1.0f, 1.0f);
		}
	}

	public static void syncToAll(MinecraftServer server, SharedTaskProgress sharedProgress) {
		PacketSyncSharedTasks packet = new PacketSyncSharedTasks(sharedProgress);
	    for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
	        PacketManager.network.sendTo(packet, player);
	    }
	}

	public NBTTagCompound toNBT() {
		NBTTagCompound root = new NBTTagCompound();
	    for (Map.Entry<String, Map<String, Integer>> jobEntry : progress.entrySet()) {
	        NBTTagCompound jobTag = new NBTTagCompound();
	        for (Map.Entry<String, Integer> taskEntry : jobEntry.getValue().entrySet()) {
	            jobTag.setInteger(taskEntry.getKey(), taskEntry.getValue());
	        }
	        root.setTag(jobEntry.getKey(), jobTag);
	    }
	    return root;
	}

	public void fromNBT(NBTTagCompound root) {
		progress.clear();
	    for (String jobId : root.getKeySet()) {
	        NBTTagCompound jobTag = root.getCompoundTag(jobId);
	        Map<String, Integer> jobProgress = new HashMap<>();
	        for (String taskId : jobTag.getKeySet()) {
	            jobProgress.put(taskId, jobTag.getInteger(taskId));
	        }
	        progress.put(jobId, jobProgress);
	    }
	}

	public String toJson() {
	    return GSON.toJson(progress);
	}

	public void fromJson(String json) {
	    progress.clear();
	    Type type = new TypeToken<Map<String, Map<String, Integer>>>(){}.getType();
	    Map<String, Map<String, Integer>> loaded = GSON.fromJson(json, type);
	    if (loaded != null) {
	        progress.putAll(loaded);
	    }
	}
}