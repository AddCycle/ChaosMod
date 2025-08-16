package net.chaos.chaosmod.jobs;

import com.google.gson.JsonObject;

import net.chaos.chaosmod.Main;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

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
		
		if (targetJson == null) {
			Main.getLogger().info("JobTaskTarget is null returning null");
			return null;
		}

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
	
	public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("type", type.ordinal());
        tag.setInteger("data", data);

        switch (type) {
            case BLOCK:
                tag.setString("block", Block.REGISTRY.getNameForObject((Block) target).toString());
                break;
            case ITEM:
                tag.setString("item", Item.REGISTRY.getNameForObject((Item) target).toString());
                break;
            case ENTITY:
                tag.setString("entity", target.toString()); // store entity as string ID
                break;
        }

        return tag;
    }

    // Load from NBT
    public static JobTaskTarget fromNBT(NBTTagCompound tag) {
        TargetType type = TargetType.values()[tag.getInteger("type")];
        int data = tag.getInteger("data");

        switch (type) {
            case BLOCK:
                Block block = Block.getBlockFromName(tag.getString("block"));
                return new JobTaskTarget(block, data, TargetType.BLOCK);

            case ITEM:
                Item item = Item.getByNameOrId(tag.getString("item"));
                return new JobTaskTarget(item, data, TargetType.ITEM);

            case ENTITY:
                String entityId = tag.getString("entity");
                return new JobTaskTarget(entityId, 0, TargetType.ENTITY);
        }

        throw new IllegalArgumentException("Unknown TargetType in NBT: " + type);
    }
}