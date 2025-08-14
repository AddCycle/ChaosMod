package net.chaos.chaosmod.jobs;

import com.google.gson.JsonObject;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class JobTaskTarget {
	public Object target;
	public int data;
	public TargetType type;

	public JobTaskTarget(Object target, int data, TargetType type) {
		this.target = target;
		this.data = data;
		this.type = type;
	}

	public JsonObject toJson() {
		JsonObject obj = new JsonObject();

        // Save by type
        switch (type) {
            case BLOCK:
                obj.addProperty("block", Block.REGISTRY.getNameForObject((Block) target).toString());
                break;
            case ITEM:
                obj.addProperty("item", Item.REGISTRY.getNameForObject((Item) target).toString());
                break;
            case ENTITY:
                obj.addProperty("entity", target.toString()); // Store as string ID
                break;
        }

        obj.addProperty("data", data);
        return obj;
    }

	public static JobTaskTarget fromJson(JsonObject json) {
		JsonObject targetJson = json.getAsJsonObject("target");

        if (targetJson.has("block")) {
            Block block = Block.getBlockFromName(targetJson.get("block").getAsString());
            int data = targetJson.has("data") ? targetJson.get("data").getAsInt() : 0;
            return new JobTaskTarget(block, data, TargetType.BLOCK);

        } else if (targetJson.has("item")) {
            Item item = Item.getByNameOrId(targetJson.get("item").getAsString());
            int data = targetJson.has("data") ? targetJson.get("data").getAsInt() : 0;
            return new JobTaskTarget(item, data, TargetType.ITEM);

        } else if (targetJson.has("entity")) {
            String entityId = targetJson.get("entity").getAsString();
            return new JobTaskTarget(entityId, 0, TargetType.ENTITY);
        }

        throw new IllegalArgumentException("Unknown target type in JSON: " + targetJson);
    }

	private static Object convert(JsonObject asJsonObject) {
		// TODO Auto-generated method stub
		return null;
	}

}