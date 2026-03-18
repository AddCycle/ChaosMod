package net.chaos.chaosmod.jobs;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
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

	public static void addExp(EntityPlayerMP player, String jobId, int amount) {
		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs != null) {
			jobs.addExp(jobId, amount);

			syncJobs(player);
		}
	}

	public void incrementTask(EntityPlayerMP player, String jobId, String taskId) {
		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);

		incrementTaskProgress(jobId, taskId);

		int progress = getTaskProgress(taskId);

		Main.getLogger().info("incremented task: {}, {}/{}", taskId, progress + 1, task.goal);

		if (progress >= task.goal) {
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

		if (getTaskProgress(taskId) >= task.goal) return;

		setTaskProgress(taskId, task.goal);

		addExp(player, jobId, task.rewardExp);
	}
	
	public static void syncJobs(EntityPlayerMP player) {
	    PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		PacketManager.network.sendTo(new PacketSyncPlayerJobs(jobs), player);
	}

	public void addExp(int amount) {
		int exp = getExp() + amount;
		int level = getLevel();

		while (exp >= getExpToNextLevel(level)) {
			exp -= getExpToNextLevel(level);
			level++;
		}

		setExp(exp);
		setLevel(level);
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}
}