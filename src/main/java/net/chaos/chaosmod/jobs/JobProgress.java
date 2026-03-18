package net.chaos.chaosmod.jobs;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketSyncPlayerJobs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class JobProgress {
	private int level;
	private int exp;

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
		if (task == null) {
			Main.getLogger().error("Task not found: {}", taskId);
			return;
		}

		if (task.progress >= task.goal) {
			completeTask(player, jobId, taskId);
		} else {
			task.progress++;
			Main.getLogger().info("incremented task: {}, {}/{}", taskId, task.progress, task.goal);
		}
		
		syncJobs(player);
	}

	public void completeTask(EntityPlayerMP player, String jobId, String taskId) {
		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
		if (task == null)
			return;

		task.progress = task.goal; // mark complete
		if (task.progress >= task.goal) {
			addExp(player, jobId, task.rewardExp);
		}
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
		return tag;
	}

	public void fromNBT(NBTTagCompound tag) {
		this.setLevel(tag.getInteger("level"));
		this.setExp(tag.getInteger("exp"));
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