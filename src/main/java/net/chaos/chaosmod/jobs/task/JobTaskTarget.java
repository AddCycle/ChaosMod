package net.chaos.chaosmod.jobs.task;

import com.google.gson.JsonObject;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.jobs.TargetType;
import net.minecraft.nbt.NBTTagCompound;

public class JobTaskTarget {
	public String target;
	public int data; // meta of the item/block
	public TargetType type;

	public JobTaskTarget(String target, int data, TargetType type) {
		this.target = target;
		this.data = data;
		this.type = type;
	}

	public JsonObject toJson() {
		JsonObject obj = new JsonObject();

        // Save by type
        switch (type) {
            case BLOCK:
                obj.addProperty("block", this.target);
                break;
            case ITEM:
                obj.addProperty("item", this.target);
                break;
            case ENTITY:
                obj.addProperty("entity", this.target);
                break;
            case BIOME:
                obj.addProperty("biome", this.target);
                break;
            case POTION:
                obj.addProperty("potion", this.target);
                break;
        }

        obj.addProperty("data", data);
        return obj;
    }

	public static JobTaskTarget fromJson(JsonObject json) {
		if (json == null) {
			Main.getLogger().info("JobTaskTarget is null returning null");
			return null;
		}

		int data = json.has("data") ? json.get("data").getAsInt() : 0;
        if (json.has("block")) {
            String block = json.get("block").getAsString();
            return new JobTaskTarget(block, data, TargetType.BLOCK);

        } else if (json.has("item")) {
            String item = json.get("item").getAsString();
            return new JobTaskTarget(item, data, TargetType.ITEM);

        } else if (json.has("entity")) {
            String entityId = json.get("entity").getAsString();
            return new JobTaskTarget(entityId, data, TargetType.ENTITY);

        } else if (json.has("biome")) {
            String biome = json.get("biome").getAsString();
            return new JobTaskTarget(biome, data, TargetType.BIOME);

        } else if (json.has("potion")) {
            String potion = json.get("potion").getAsString();
            return new JobTaskTarget(potion, data, TargetType.POTION);
        }

        throw new IllegalArgumentException("Unknown target type in JSON: " + json);
    }
	
	public NBTTagCompound toNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("type", type.ordinal());
        tag.setInteger("data", data);

        switch (type) {
            case BLOCK:
                tag.setString("block", this.target);
                break;
            case ITEM:
                tag.setString("item", this.target);
                break;
            case ENTITY:
                tag.setString("entity", this.target);
                break;
            case BIOME:
                tag.setString("biome", this.target);
                break;
            case POTION:
                tag.setString("potion", this.target);
                break;
        }

        return tag;
    }

    public static JobTaskTarget fromNBT(NBTTagCompound tag) {
        TargetType type = TargetType.values()[tag.getInteger("type")];
        int data = tag.getInteger("data");

        switch (type) {
            case BLOCK:
                String block = tag.getString("block");
                return new JobTaskTarget(block, data, TargetType.BLOCK);

            case ITEM:
                String item = tag.getString("item");
                return new JobTaskTarget(item, data, TargetType.ITEM);

            case ENTITY:
                String entity = tag.getString("entity");
                return new JobTaskTarget(entity, 0, TargetType.ENTITY);

            case BIOME:
                String biome = tag.getString("biome");
                return new JobTaskTarget(biome, 0, TargetType.BIOME);

            case POTION:
                String potion = tag.getString("potion");
                return new JobTaskTarget(potion, data, TargetType.POTION);
        }

        throw new IllegalArgumentException("Unknown TargetType in NBT: " + type);
    }
}