package net.chaos.chaosmod.jobs;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.jobs.task.JobTaskManager;
import util.handlers.DataLoader;

public class JobsManager {
	public static final Map<String, Job> REGISTRY = new LinkedHashMap<>();
	public static final JobTaskManager TASK_MANAGER = new JobTaskManager();

	public static Job FIGHTER;
	public static Job FARMER;
	public static Job ALCHEMIST;
	public static Job TAMER;
	public static Job MINER;
	public static Job FISHERMAN;
	public static Job TRAVELER;
//	public static Job HUNTER;

    private static final Gson GSON = new Gson();

    /**
     * load the jobs data (server-side only)
     */
    public static void init() {
        FIGHTER = loadJob("origins/fighter");
        FARMER = loadJob("origins/farmer");
        ALCHEMIST = loadJob("origins/alchemist");
        TAMER = loadJob("origins/tamer");
        MINER = loadJob("origins/miner");
        FISHERMAN = loadJob("origins/fisherman");
        TRAVELER = loadJob("origins/traveler");
//        HUNTER = loadJob("origins/hunter");
		displayAll();
		JobTaskManager.initTasks();
    }

    private static Job loadJob(String path) {
        JsonObject json = DataLoader.loadServerJson("chaosmod_jobs/" + path + ".json");
        if (json != null && json.has("id") && json.has("name")) {
            return Job.fromJson(json);
        }
        return null;
    }

    /**
     * Convert all jobs into a JSON string for sending
     * @return
     */
    public static String toJsonString() {
        JsonArray arr = new JsonArray();
        if (FIGHTER != null) arr.add(FIGHTER.toJson());
        if (FARMER != null) arr.add(FARMER.toJson());
        if (ALCHEMIST != null) arr.add(ALCHEMIST.toJson());
        if (TAMER != null) arr.add(TAMER.toJson());
        if (MINER != null) arr.add(MINER.toJson());
        if (FISHERMAN != null) arr.add(FISHERMAN.toJson());
        if (TRAVELER != null) arr.add(TRAVELER.toJson());
//        if (HUNTER != null) arr.add(HUNTER.toJson());
        return GSON.toJson(arr);
    }

    /**
     * Load jobs from JSON string (client-side) (used in packetSyncJobs)
     * @param jsonString
     */
    public static void loadFromJson(String jsonString) {
    	int id = 0;
		Main.getLogger().info("LOADING FROM JSON");
        JsonArray arr = GSON.fromJson(jsonString, JsonArray.class);
        if (arr == null) {
        	Main.getLogger().trace("ARRAY NULL not loading from json");
        	return;
        }

        FIGHTER = Job.fromJson(arr.get(id++).getAsJsonObject());
        FARMER  = Job.fromJson(arr.get(id++).getAsJsonObject());
        ALCHEMIST = Job.fromJson(arr.get(id++).getAsJsonObject());
        TAMER   = Job.fromJson(arr.get(id++).getAsJsonObject());
        MINER   = Job.fromJson(arr.get(id++).getAsJsonObject());
        FISHERMAN   = Job.fromJson(arr.get(id++).getAsJsonObject());
        TRAVELER   = Job.fromJson(arr.get(id++).getAsJsonObject());
//        HUNTER  = Job.fromJson(arr.get(id++).getAsJsonObject());
        displayAll();
    }

	public static int nextId() {
		return REGISTRY.size();
	}
	
	public static void displayAll() {
		Main.getLogger().info("************** JobRegistry **************");
		REGISTRY.forEach((id, job) -> Main.getLogger().info("Job : {}", id));
	}
	
	public static Job getJobByIndex(int index) {
	    return REGISTRY.values().stream()
	            .filter(job -> job.index == index)
	            .findFirst()
	            .orElse(null);
	}
}