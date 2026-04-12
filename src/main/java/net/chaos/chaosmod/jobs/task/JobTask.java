package net.chaos.chaosmod.jobs.task;

import static net.chaos.chaosmod.jobs.task.TaskType.fromName;

import com.google.gson.JsonObject;

import util.Reference;

public class JobTask {
	public String id;
	public String name;
	public String description;
	public TaskType type;
	public JobTaskTarget target;
	public int goal;
	public int rewardExp;
	public boolean shared;

	public JobTask(String id, String name, String description, TaskType type, JobTaskTarget target, int goal, int rewardExp, boolean shared) {
		this.id = id.contains(":") ? id : Reference.PREFIX + id;
		this.name = name;
		this.description = description;
		this.type = type;
		this.target = target;
		this.goal = goal;
		this.rewardExp = rewardExp;
		this.shared = shared;
	}

	public JsonObject toJson() {
        JsonObject obj = new JsonObject();
        obj.addProperty("id", id);
        obj.addProperty("name", name);
        obj.addProperty("description", description);
        obj.addProperty("type", type.name());
        if (target != null) {
            obj.add("target", target.toJson());
        } else {
            obj.add("target", new JsonObject());
        }
        obj.addProperty("goal", goal);
        obj.addProperty("rewardExp", rewardExp);
        obj.addProperty("shared", shared);

        return obj;
    }

    public static JobTask fromJson(JsonObject json) {
        return new JobTask(
            json.get("id").getAsString(),
            json.get("name").getAsString(),
            json.get("description").getAsString(),
            fromName(json.get("type").getAsString()),
            convert(json.get("target").getAsJsonObject()),
            json.has("goal") ? json.get("goal").getAsInt() : 1,
            json.has("rewardExp") ? json.get("rewardExp").getAsInt() : 0,
            json.has("shared") ? json.get("shared").getAsBoolean() : false
        );
    }

    public static JobTaskTarget convert(JsonObject json) {
        return JobTaskTarget.fromJson(json);
    }
}