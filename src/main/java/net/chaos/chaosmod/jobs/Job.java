package net.chaos.chaosmod.jobs;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;

import net.chaos.chaosmod.Main;
import util.Reference;

public class Job {
	public String id;
	public int index;
	public String name;
	public String description;
	public final List<JobTask> tasks = new ArrayList<JobTask>();

	public Job(String id, String name) {
		this.id = Reference.PREFIX + id;
		this.name = name;
		this.index = JobsManager.nextId();
		
		JobsManager.JOBS_REGISTRY.add(this);
		Main.getLogger().info("REGISTERING JOB : {}", this.id);
	}
	
	public void addTask(JobTask task) {
		this.tasks.add(task);
	}

	public void removeTask(JobTask task) {
		this.tasks.remove(task);
	}
	
	public void getTasksCount() {
		this.tasks.size();
	}
	
	public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", id);
        obj.addProperty("name", name);
        return obj;
    }

    public static Job fromJson(JsonObject json) {
        return new Job(
            json.get("id").getAsString(),
            json.get("name").getAsString()
        );
    }
}
