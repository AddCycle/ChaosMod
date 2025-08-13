package net.chaos.chaosmod.jobs;

import com.google.gson.JsonObject;

import util.handlers.DataLoader;

public class JobsList {
	public static final Job FIGHTER_JOB = new Job("fighter", "Fighter");
	public static final Job FARMER = new Job("farmer", "Farmer");
	public static final Job HUNTER = new Job("hunter", "Hunter");
	public static final Job ALCHEMIST = new Job("alchemist", "Alchemist");
	public static final Job TAMER = new Job("tamer", "Tamer"); // pr Jorus !!
	public static final Job TEST1 = new Job("test_entry1", "Test Entry 1");
	public static final Job TEST2 = new Job("test_entry2", "Test Entry 2");
	public static final Job TEST3 = new Job("test_entry3", "Test Entry 3");
	public static final Job TEST4 = new Job("test_entry4", "Test Entry 4");
	public static final Job TEST5 = new Job("test_entry5", "Test Entry 5");
	public static final Job TEST6 = new Job("test_entry6", "Test Entry 6");
	public static Job TEST7;
	
	public static void init() {
		JsonObject json = DataLoader.loadJson("chaosmod_jobs/origins/fighter.json");
		if (json != null) {
			TEST7 = new Job(json.get("name").getAsString(), json.get("description").getAsString());
		}
	}
	
	public static Job load(String path) {
		return null;
	}
}