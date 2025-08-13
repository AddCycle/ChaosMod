package net.chaos.chaosmod.jobs;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.chaos.chaosmod.Main;
import util.handlers.DataLoader;

public class JobsManager {
	public static final List<Job> JOBS_REGISTRY = new ArrayList<Job>();

	public static Job FIGHTER;
    public static Job FARMER;
    public static Job HUNTER;
    public static Job ALCHEMIST;
    public static Job TAMER;

    private static final Gson GSON = new Gson();

    public static void init() {
        FIGHTER = loadJob("origins/fighter");
        FARMER = loadJob("origins/farmer");
        HUNTER = loadJob("origins/hunter");
        ALCHEMIST = loadJob("origins/alchemist");
        TAMER = loadJob("origins/tamer");
		displayAll();
    }

    private static Job loadJob(String path) {
        JsonObject json = DataLoader.loadServerJson("chaosmod_jobs/" + path + ".json");
        if (json != null && json.has("id") && json.has("name")) {
            return Job.fromJson(json);
        }
        return null;
    }

    // Convert all jobs into a JSON string for sending
    public static String toJsonString() {
        JsonArray arr = new JsonArray();
        if (FIGHTER != null) arr.add(FIGHTER.toJson());
        if (FARMER != null) arr.add(FARMER.toJson());
        if (HUNTER != null) arr.add(HUNTER.toJson());
        if (ALCHEMIST != null) arr.add(ALCHEMIST.toJson());
        if (TAMER != null) arr.add(TAMER.toJson());
        return GSON.toJson(arr);
    }

    // Load jobs from JSON string (client-side)
    public static void loadFromJson(String jsonString) {
        JsonArray arr = GSON.fromJson(jsonString, JsonArray.class);
        FIGHTER = Job.fromJson(arr.get(0).getAsJsonObject());
        FARMER  = Job.fromJson(arr.get(1).getAsJsonObject());
        HUNTER  = Job.fromJson(arr.get(2).getAsJsonObject());
        ALCHEMIST = Job.fromJson(arr.get(3).getAsJsonObject());
        TAMER   = Job.fromJson(arr.get(4).getAsJsonObject());
        displayAll();
    }

    // ****************** BEFORE **********************

	public static int nextId() {
		return JOBS_REGISTRY.size();
	}
	
	public static void displayAll() {
		Main.getLogger().info("************** JobRegistry **************");
		JOBS_REGISTRY.forEach(job -> Main.getLogger().info("Job : {}", job.id));
	}
}
