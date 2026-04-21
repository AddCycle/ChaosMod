package net.chaos.chaosmod.jobs.data;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.chaos.chaosmod.jobs.task.SharedTaskProgress;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientSharedTaskCache {
	private static SharedTaskProgress cache = new SharedTaskProgress();

    private static final Gson GSON = new Gson();

    public static void load(String json) {
        cache = new SharedTaskProgress();
        cache.fromJson(json);
    }

    public static int getTaskProgress(String jobId, String taskId) {
        return cache.getTaskProgress(jobId, taskId);
    }

    // New: per-player contributions
    public static Map<UUID, Integer> getContributions(String jobId, String taskId) {
        return cache.getContributions(jobId, taskId);
    }

	// Same structure as server: jobId -> taskId -> progress
//    private static Map<String, Map<String, Integer>> cache = new HashMap<>();

//    public static void load(String json) {
//        cache.clear();
//        Type type = new TypeToken<Map<String, Map<String, Integer>>>(){}.getType();
//        Map<String, Map<String, Integer>> loaded = GSON.fromJson(json, type);
//        if (loaded != null) {
//            cache.putAll(loaded);
//        }
//    }

//    public static int getProgress(String jobId, String taskId) {
//        return cache.getOrDefault(jobId, Collections.emptyMap()).getOrDefault(taskId, 0);
//    }

    public static boolean isComplete(String jobId, String taskId) {
        JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
        if (task == null) return false;
        return getTaskProgress(jobId, taskId) >= task.goal;
    }

    // For rendering a progress bar: returns 0.0 - 1.0
    public static float getProgressRatio(String jobId, String taskId) {
        JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
        if (task == null || task.goal <= 0) return 0f;
        return (float) getTaskProgress(jobId, taskId) / task.goal;
    }
}