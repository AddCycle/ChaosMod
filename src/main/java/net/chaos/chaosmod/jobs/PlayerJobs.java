package net.chaos.chaosmod.jobs;

import java.util.HashMap;
import java.util.Map;

import net.chaos.chaosmod.Main;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerJobs implements ICapabilitySerializable<NBTTagCompound>{
	private final Map<String, JobProgress> progressMap = new HashMap<>();
	
	public void addExp(String jobId, int amount) {
        JobProgress progress = progressMap.computeIfAbsent(jobId, id -> new JobProgress(0, 0));
        if (progress != null) {
        	Main.getLogger().info("called progress adding exp amount : {}", amount);
            progress.addExp(amount);
        }
    }

    public JobProgress getProgress(String jobId) {
    	return progressMap.computeIfAbsent(jobId, id -> new JobProgress(0, 0));
    }

    public void setProgress(String jobId, JobProgress progress) {
        progressMap.put(jobId, progress);
    }

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityPlayerJobs.PLAYER_JOBS;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityPlayerJobs.PLAYER_JOBS ? (T) this : null;
	}

	@Override
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

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		progressMap.clear();

        NBTTagList jobList = nbt.getTagList("jobs", 10); // 10 = NBTTagCompound type
        for (int i = 0; i < jobList.tagCount(); i++) {
            NBTTagCompound jobTag = jobList.getCompoundTagAt(i);
            String jobId = jobTag.getString("jobId");
            JobProgress progress = new JobProgress(0, 0);
            progress.fromNBT(jobTag.getCompoundTag("progress"));
            progressMap.put(jobId, progress);
        }
	}

}