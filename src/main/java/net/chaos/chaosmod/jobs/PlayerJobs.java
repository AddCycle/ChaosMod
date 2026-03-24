package net.chaos.chaosmod.jobs;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Per-player job capabilities storage client-side (unless sync-system made by the user (which I did))
 */
public class PlayerJobs {
	private final Map<String, JobProgress> progressMap = new HashMap<>();
	
	/**
	 * adds exp to the job specified by id in handled by JobProgress
	 * @param jobId
	 * @param amount
	 */
	public void addExp(EntityPlayerMP player, String jobId, int amount) {
        JobProgress progress = progressMap.computeIfAbsent(jobId, id -> new JobProgress(0, 0));
        if (progress != null) {
            progress.addExp(player, jobId, amount);
        }
    }

	/**
	 * Returns the job current progress (level,xp)
	 * @param jobId
	 * @return
	 */
    public JobProgress getProgress(String jobId) {
    	return progressMap.computeIfAbsent(jobId, id -> new JobProgress(0, 0));
    }

    /**
     * Directly modify the jobProgress of the job
     * @param jobId
     * @param progress
     */
    public void setProgress(String jobId, JobProgress progress) {
        progressMap.put(jobId, progress);
    }

	public NBTTagCompound serializeNBT() {
		NBTTagCompound tag = new NBTTagCompound();

        NBTTagList jobList = new NBTTagList();
        for (Map.Entry<String, JobProgress> entry : progressMap.entrySet()) {
            NBTTagCompound jobTag = new NBTTagCompound();
            jobTag.setString("jobId", entry.getKey());
            jobTag.setTag("progress", entry.getValue().toNBT());
            jobList.appendTag(jobTag);
        }

        tag.setTag("jobs", jobList);
        return tag;
	}

	public void deserializeNBT(NBTTagCompound nbt) {
		progressMap.clear();

        NBTTagList jobList = nbt.getTagList("jobs", 10);
        for (int i = 0; i < jobList.tagCount(); i++) {
            NBTTagCompound jobTag = jobList.getCompoundTagAt(i);
            String jobId = jobTag.getString("jobId");
            JobProgress progress = new JobProgress(0, 0);
            progress.fromNBT(jobTag.getCompoundTag("progress"));
            progressMap.put(jobId, progress);
        }
	}

	public void copyFrom(PlayerJobs oldCap) {
		progressMap.clear();
		progressMap.putAll(oldCap.progressMap);
	}
}