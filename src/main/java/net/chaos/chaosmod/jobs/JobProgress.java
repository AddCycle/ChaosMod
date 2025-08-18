package net.chaos.chaosmod.jobs;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.network.PacketManager;
import net.chaos.chaosmod.network.PacketSyncPlayerJobs;
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
		Main.getLogger().info("Jobs(JobProgress.addExp): player has capability jobs ? {}", jobs.hasCapability(CapabilityPlayerJobs.PLAYER_JOBS, null));
		if (jobs != null) {
			Main.getLogger().info("Jobs : Adding exp method call for job = {} : {}", jobId, amount);
			jobs.addExp(jobId, amount);

			// Send updated data to client
			PacketManager.network.sendTo(
					new PacketSyncPlayerJobs(jobs),
					player
					);
			Main.getLogger().info("Packets sent SyncPlayerJobs(jobs) capabilities");
		}
	}

	public void completeTask(EntityPlayerMP player, String jobId, String taskId) {
        JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
        if (task == null) return;

        task.progress = task.goal; // mark complete
        if (task.progress >= task.goal) {
            addExp(player, jobId, task.rewardExp);
        }
    }

	public void addExp(int amount) {
	    int exp = getExp() + amount;
	    int level = getLevel();
	    Main.getLogger().info("level : {}", getLevel());
	    Main.getLogger().info("exp : {}", getExp());
	    Main.getLogger().info("next level xp requirement : {}", getExpToNextLevel(level));

	    while (exp >= getExpToNextLevel(level)) { // pass level, not call getLevel()
	        exp -= getExpToNextLevel(level);
	        level++;
	    }

	    setExp(exp);
	    setLevel(level);
	}

	private int getExpToNextLevel(int level) {
		Main.getLogger().info("Job : exp to next level : {}", 100 + (level * 50));
		return 100 + (level * 50);
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