package net.chaos.chaosmod.jobs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;

public class JobTaskManager {
	// jobId, activeTasksList
	private Map<String, Map<String, JobTask>> tasks = new HashMap<>();

	public Collection<JobTask> getTasks(String jobId) {
        return tasks.getOrDefault(jobId, Collections.emptyMap()).values();
    }
	
	public JobTask getTask(String jobId, String taskId) {
        Map<String, JobTask> jobTasks = tasks.get(jobId);
        if (jobTasks != null) {
            return jobTasks.get(taskId);
        }
        return null;
    }
	
	public void addTask(String jobId, JobTask task) {
        tasks.computeIfAbsent(jobId, k -> new HashMap<>()).put(task.id, task);
    }
	
	public void incrementProgress(String jobId, String taskId, int amount) {
        JobTask task = getTask(jobId, taskId);
        if (task != null) {
            task.progress += amount;
            if (task.progress > task.goal) {
                task.progress = task.goal;
            }
        }
    }
	
	public boolean isTaskComplete(String jobId, String taskId) {
        JobTask task = getTask(jobId, taskId);
        return task != null && task.progress >= task.goal;
    }
	
	public NBTTagCompound toNBT() {
	    NBTTagCompound tag = new NBTTagCompound();
	    for (Map.Entry<String, Map<String, JobTask>> jobEntry : tasks.entrySet()) {
	        String jobId = jobEntry.getKey();
	        NBTTagCompound jobTag = new NBTTagCompound();
	        for (JobTask task : jobEntry.getValue().values()) {
	            NBTTagCompound taskTag = new NBTTagCompound();
	            taskTag.setString("id", task.id);
	            taskTag.setString("name", task.name);
	            taskTag.setString("description", task.description);
	            taskTag.setString("type", task.type.name());
	            if (task.target != null) {
	                taskTag.setTag("target", task.target.toNBT());
	            }
	            taskTag.setInteger("progress", task.progress);
	            taskTag.setInteger("goal", task.goal);

	            jobTag.setTag(task.id, taskTag);
	        }
	        tag.setTag(jobId, jobTag);
	    }
	    return tag;
	}

	// Load from NBT
	public void fromNBT(NBTTagCompound tag) {
	    tasks.clear();
	    for (String jobId : tag.getKeySet()) {
	        NBTTagCompound jobTag = tag.getCompoundTag(jobId);
	        Map<String, JobTask> jobTasks = new HashMap<>();
	        for (String taskId : jobTag.getKeySet()) {
	            NBTTagCompound taskTag = jobTag.getCompoundTag(taskId);
	            JobTask task = new JobTask(
	                taskTag.getString("id"),
	                taskTag.getString("name"),
	                taskTag.getString("description"),
	                TaskType.valueOf(taskTag.getString("type")),
	                taskTag.hasKey("target") ? JobTaskTarget.fromNBT(taskTag.getCompoundTag("target")) : null,
	                taskTag.getInteger("progress"),
	                taskTag.getInteger("goal")
	            );
	            jobTasks.put(task.id, task);
	        }
	        tasks.put(jobId, jobTasks);
	    }
	}
}
