package net.chaos.chaosmod.jobs.task;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

// FIXME : update
public class SharedTaskProgress {
	// changed to:  Map<String, Map<String, Map<UUID, Integer>>>  jobId -> taskId -> uuid -> contribution
	private final Map<String, Map<String, Map<UUID, Integer>>> progress = new HashMap<>();
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
        return progress
            .getOrDefault(jobId, Collections.emptyMap())
            .getOrDefault(taskId, Collections.emptyMap())
            .values().stream()
            .mapToInt(Integer::intValue)
            .sum(); // total is the sum of all contributions
    }

	public Map<UUID, Integer> getContributions(String jobId, String taskId) {
        return Collections.unmodifiableMap(
            progress
                .getOrDefault(jobId, Collections.emptyMap())
                .getOrDefault(taskId, Collections.emptyMap())
        );
    }
	
	public void incrementTask(MinecraftServer server, World world, String jobId, String taskId, int amount, UUID contributor) {
        JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
        if (task == null) return;

        int totalBefore = getTaskProgress(jobId, taskId);

        Map<UUID, Integer> contributions = progress
            .computeIfAbsent(jobId, k -> new HashMap<>())
            .computeIfAbsent(taskId, k -> new HashMap<>());

        int current = contributions.getOrDefault(contributor, 0);
        int cap = task.goal; // individual contribution capped at goal too
        contributions.put(contributor, Math.min(current + amount, cap));

        int totalAfter = getTaskProgress(jobId, taskId);
        // clamp total to goal
        if (totalAfter > task.goal) {
            contributions.put(contributor, contributions.get(contributor) - (totalAfter - task.goal));
        }

        MapStorage storage = world.getMapStorage();
        SharedTaskProgressData data = (SharedTaskProgressData) storage.getOrLoadData(
            SharedTaskProgressData.class, SharedTaskProgressData.NAME);
        if (data != null) data.markDirty();

        syncToAll(server, this);

        if (totalBefore < task.goal && getTaskProgress(jobId, taskId) >= task.goal) {
            onSharedTaskComplete(server, jobId, taskId);
        }
    }

	private void onSharedTaskComplete(MinecraftServer server, String jobId, String taskId) {
		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);

		// FIXME : make a system in the interface to click a button to redeem the common
		// task rewards or add the exp for the player
		// but I think a global reward will be better than just increasing one level
		// and receiving little rewards for job tasks before anyone
		// and also messing up the leveling system because you just got 3 rewards instead
		// of doing the tasks along
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

	// NBT
    public NBTTagCompound toNBT() {
        NBTTagCompound root = new NBTTagCompound();
        for (Map.Entry<String, Map<String, Map<UUID, Integer>>> jobEntry : progress.entrySet()) {
            NBTTagCompound jobTag = new NBTTagCompound();
            for (Map.Entry<String, Map<UUID, Integer>> taskEntry : jobEntry.getValue().entrySet()) {
                NBTTagCompound taskTag = new NBTTagCompound();
                for (Map.Entry<UUID, Integer> contrib : taskEntry.getValue().entrySet()) {
                    taskTag.setInteger(contrib.getKey().toString(), contrib.getValue());
                }
                jobTag.setTag(taskEntry.getKey(), taskTag);
            }
            root.setTag(jobEntry.getKey(), jobTag);
        }
        return root;
    }

    public void fromNBT(NBTTagCompound root) {
        progress.clear();
        for (String jobId : root.getKeySet()) {
            NBTTagCompound jobTag = root.getCompoundTag(jobId);
            for (String taskId : jobTag.getKeySet()) {
                NBTTagCompound taskTag = jobTag.getCompoundTag(taskId);
                Map<UUID, Integer> contributions = new HashMap<>();
                for (String uuidStr : taskTag.getKeySet()) {
                    contributions.put(UUID.fromString(uuidStr), taskTag.getInteger(uuidStr));
                }
                progress
                    .computeIfAbsent(jobId, k -> new HashMap<>())
                    .put(taskId, contributions);
            }
        }
    }
    
    // JSON (for packet)
    public String toJson() {
        // Convert UUID keys to strings for Gson
        Map<String, Map<String, Map<String, Integer>>> serializable = new HashMap<>();
        for (Map.Entry<String, Map<String, Map<UUID, Integer>>> jobEntry : progress.entrySet()) {
            Map<String, Map<String, Integer>> jobMap = new HashMap<>();
            for (Map.Entry<String, Map<UUID, Integer>> taskEntry : jobEntry.getValue().entrySet()) {
                Map<String, Integer> contribMap = new HashMap<>();
                taskEntry.getValue().forEach((uuid, val) -> contribMap.put(uuid.toString(), val));
                jobMap.put(taskEntry.getKey(), contribMap);
            }
            serializable.put(jobEntry.getKey(), jobMap);
        }
        return GSON.toJson(serializable);
    }

    public void fromJson(String json) {
        progress.clear();
        Type type = new TypeToken<Map<String, Map<String, Map<String, Integer>>>>(){}.getType();
        Map<String, Map<String, Map<String, Integer>>> loaded = GSON.fromJson(json, type);
        if (loaded == null) return;
        for (Map.Entry<String, Map<String, Map<String, Integer>>> jobEntry : loaded.entrySet()) {
            for (Map.Entry<String, Map<String, Integer>> taskEntry : jobEntry.getValue().entrySet()) {
                Map<UUID, Integer> contributions = new HashMap<>();
                taskEntry.getValue().forEach((uuidStr, val) -> contributions.put(UUID.fromString(uuidStr), val));
                progress
                    .computeIfAbsent(jobEntry.getKey(), k -> new HashMap<>())
                    .put(taskEntry.getKey(), contributions);
            }
        }
    }
}