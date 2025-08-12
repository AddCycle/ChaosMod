package net.chaos.chaosmod.jobs;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.Main;

public class JobsManager {
	public static final List<Job> JOBS_REGISTRY = new ArrayList<Job>();
	
	public static void init() {
		JobsList.init();
		displayAll();
	}

	public static int nextId() {
		return JOBS_REGISTRY.size();
	}
	
	public static void displayAll() {
		Main.getLogger().info("************** JobRegistry **************");
		JOBS_REGISTRY.forEach(job -> Main.getLogger().info("Job : {}", job.id));
	}
}
