package net.chaos.chaosmod.jobs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.reward.JobReward;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketSyncPlayerJobs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class JobProgress {
	private int level;
	private int exp;
	private Map<String, Integer> taskProgressMap = new HashMap<>();

	public JobProgress(int level, int exp) {
		this.level = level;
		this.exp = exp;
	}

	public void incrementTask(EntityPlayerMP player, String jobId, String taskId) {
		incrementTask(player, jobId, taskId, 1);
	}

	public void incrementTask(EntityPlayerMP player, String jobId, String taskId, int amount) {
		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);

		int before = getTaskProgress(taskId);

		incrementTaskProgress(jobId, taskId, amount);

		int after = getTaskProgress(taskId);

		Main.getLogger().info("incremented task: {}, {}/{}", taskId, after, task.goal);

		if (before < task.goal && after >= task.goal) {
			Main.getLogger().info("invoking completeTask");
			completeTask(player, jobId, taskId);
		}

		syncJobs(player);
	}

	public void incrementTaskProgress(String jobId, String taskId) {
		incrementTaskProgress(jobId, taskId, 1);
	}

	public void incrementTaskProgress(String jobId, String taskId, int amount) {
		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);

		int current = getTaskProgress(taskId);
		taskProgressMap.put(taskId, Math.min(current + amount, task.goal));
	}

	public int getTaskProgress(String taskId) {
		return taskProgressMap.getOrDefault(taskId, 0);
	}

	public void setTaskProgress(String taskId, int value) {
		taskProgressMap.put(taskId, value);
	}

	public void completeTask(EntityPlayerMP player, String jobId, String taskId) {
		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
		if (task == null)
			return;

		addExp(player, jobId, task.rewardExp);
	}

	public static void syncJobs(EntityPlayerMP player) {
		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		PacketManager.network.sendTo(new PacketSyncPlayerJobs(jobs), player);
	}

	public void addExp(EntityPlayerMP player, String jobId, int amount) {
		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);

		if (jobs != null) {
			int oldExp = getExp();
			int exp = getExp() + amount;
			int level = getLevel();

			while (exp >= getExpToNextLevel(level)) {
				exp -= getExpToNextLevel(level);
				level++;
				onLevelUp(player, jobId, level);
			}

			setExp(exp);
			setLevel(level);

			Main.getLogger().info("Job EXP added: {} -> {}, Level now: {}", oldExp, exp, level);
		}

		syncJobs(player);
	}

	private void onLevelUp(EntityPlayerMP player, String jobId, int level) {
		Main.getLogger().info("onLevelUp trigger for job : {}, level: {}", jobId, level);
		Job job = JobsManager.REGISTRY.get(jobId);
		if (job == null) {
			Main.getLogger().error("Job is null cannot invoke levelUp: {}", jobId);
			return;
		}

		if (job.rewards != null) {
			Main.getLogger().info("job.rewards != null");
			List<JobReward> reward = job.rewards.stream().filter(r -> r.getLevel() == level).collect(Collectors.toList());

			if (reward != null) {
				reward.forEach(r -> r.give(player));
				Main.getLogger().info("rewards given to player");
			} else {
				Main.getLogger().error("rewards are null cannot grant it for level: {} and job: {}", level, jobId);
			}
		}
	}

	public int getExpToNextLevel(int currentLevel) {
		return 100 + (currentLevel * 50);
	}

	public NBTTagCompound toNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("level", getLevel());
		tag.setInteger("exp", getExp());

		NBTTagCompound tasksTag = new NBTTagCompound();

		for (Entry<String, Integer> entry : taskProgressMap.entrySet()) {
			tasksTag.setInteger(entry.getKey(), entry.getValue());
		}

		tag.setTag("tasks", tasksTag);

		return tag;
	}

	public void fromNBT(NBTTagCompound tag) {
		this.setLevel(tag.getInteger("level"));
		this.setExp(tag.getInteger("exp"));

		if (tag.hasKey("tasks")) {
			NBTTagCompound tasksTag = tag.getCompoundTag("tasks");

			for (String key : tasksTag.getKeySet()) {
				taskProgressMap.put(key, tasksTag.getInteger(key));
			}
		}
	}

	public int getLevel() { return level; }

	public void setLevel(int level) { this.level = level; }

	public int getExp() { return exp; }

	public void setExp(int exp) { this.exp = exp; }
}