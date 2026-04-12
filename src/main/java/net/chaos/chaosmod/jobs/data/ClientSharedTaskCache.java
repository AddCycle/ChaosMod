package net.chaos.chaosmod.jobs.data;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.task.JobTask;

public class ClientSharedTaskCache {
	// Same structure as server: jobId -> taskId -> progress
    private static Map<String, Map<String, Integer>> cache = new HashMap<>();

    private static final Gson GSON = new Gson();

    public static void load(String json) {
        cache.clear();
        Type type = new TypeToken<Map<String, Map<String, Integer>>>(){}.getType();
        Map<String, Map<String, Integer>> loaded = GSON.fromJson(json, type);
        if (loaded != null) {
            cache.putAll(loaded);
        }
    }

    public static int getProgress(String jobId, String taskId) {
        return cache.getOrDefault(jobId, Collections.emptyMap()).getOrDefault(taskId, 0);
    }

    public static boolean isComplete(String jobId, String taskId) {
        JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
        if (task == null) return false;
        return getProgress(jobId, taskId) >= task.goal;
    }

    // For rendering a progress bar: returns 0.0 - 1.0
    public static float getProgressRatio(String jobId, String taskId) {
        JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
        if (task == null || task.goal <= 0) return 0f;
        return (float) getProgress(jobId, taskId) / task.goal;
    }
}