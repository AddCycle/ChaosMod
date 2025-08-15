package net.chaos.chaosmod.jobs;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.chaos.chaosmod.Main;
import util.Reference;

public class Job {
	public String id;
	public int index;
	public String name;
	public String description;
	public int maxLevel;
	public List<JobTask> tasks;
	// public List<JobReward> rewards = new ArrayList<JobReward>();

	public Job(String id, String name, List<JobTask> tasks, String description, int maxLevel) {
		this.id = id.contains(":") ? id : Reference.PREFIX + id;
		this.name = name;
		Job existing = JobsManager.REGISTRY.get(this.id);
	    if (existing != null) {
	        this.index = existing.index; // keeps old index if existing
	    } else {
	        this.index = JobsManager.nextId(); // new index on init
	    }
		this.tasks = tasks != null ? tasks : new ArrayList<>();
		this.description = description;
		this.maxLevel = maxLevel;
		
		JobsManager.REGISTRY.put(this.id, this);
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
        obj.addProperty("id", this.id);
        obj.addProperty("name", this.name);
        obj.addProperty("description", this.description);
        obj.addProperty("maxLevel", this.maxLevel);

        JsonArray tasksArr = new JsonArray();
        for (JobTask task : tasks) {
            tasksArr.add(task.toJson());
        }
        obj.add("tasks", tasksArr);

        return obj;
    }

    public static Job fromJson(JsonObject json) {
    	Job job = new Job(
            json.get("id").getAsString(),
            json.get("name").getAsString(),
            json.has("tasks") ? convert(json.getAsJsonArray("tasks")) : new ArrayList<JobTask>(),
            json.has("description") ? json.get("description").getAsString() : "default_description",
            json.has("maxLevel") ? json.get("maxLevel").getAsInt() : 20
        );
    	Main.getLogger().info("job fromJson : {}", job.id);
    	Main.getLogger().info("TASKS is null ? {} ", job.tasks == null);
        return job;
    }
    
    private static List<JobTask> convert(JsonArray array) {
    	List<JobTask> list = new ArrayList<>();
        if (array != null || !array.isJsonNull()) {
            for (JsonElement el : array) {
                list.add(JobTask.fromJson(el.getAsJsonObject()));
            }
        }
        return list;
    }
    
    // TODO : REFACTOR : to not do all optional checks in the fromJson part
    /*private static Job build(JsonObject json) {
    	
    }*/
}
