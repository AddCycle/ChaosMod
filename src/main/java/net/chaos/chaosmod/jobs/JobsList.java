package net.chaos.chaosmod.jobs;

import com.google.gson.JsonObject;

import util.handlers.DataLoader;

public class JobsList {
	public static Job FIGHTER_JOB;
	public static Job FARMER;
	public static Job HUNTER;
	public static Job ALCHEMIST;
	public static Job TAMER; // pr Jorus !!
	
	public static void init() {
		loadJob("origins/fighter", FIGHTER_JOB);
		loadJob("origins/farmer", FARMER);
		loadJob("origins/hunter", HUNTER);
		loadJob("origins/alchemist", ALCHEMIST);
		loadJob("origins/tamer", TAMER);
	}
	
	public static void loadJob(String path, Job job) {
		JsonObject json = DataLoader.loadServerJson("chaosmod_jobs/" + path + ".json");
		if (json != null && json.has("id") && json.has("name")) {
			job = new Job(json.get("id").getAsString(), json.get("name").getAsString());
		}
	}
}