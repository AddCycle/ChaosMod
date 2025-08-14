package net.chaos.chaosmod.jobs;

import static net.chaos.chaosmod.jobs.TaskType.fromName;

import com.google.gson.JsonObject;

import util.Reference;

// wire to the forge hooks/events in order to detect all the changes with block broken/harvested more accurately
public class JobTask {
	public String id;
	public String name;
	public String description;
	public TaskType type;
	public String target;
	public int progress;
	public int goal;

	public JobTask(String id, String name, String description, TaskType type, String target, int progress, int goal) {
		this.id = id.contains(":") ? id : Reference.PREFIX + id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.target = target;
		this.progress = progress;
		this.goal = goal;
	}

	public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", id);
        obj.addProperty("name", name);
        obj.addProperty("description", description);
        obj.addProperty("type", type.name());
        obj.addProperty("target", target);
        obj.addProperty("progress", progress);
        obj.addProperty("goal", goal);

        return obj;
    }

    public static JobTask fromJson(JsonObject json) {
        return new JobTask(
            json.get("id").getAsString(),
            json.get("name").getAsString(),
            json.get("description").getAsString(),
            fromName(json.get("type").getAsString()),
            json.get("target").getAsString(),
            json.get("progress").getAsInt(),
            json.get("goal").getAsInt()
        );
    }

}
