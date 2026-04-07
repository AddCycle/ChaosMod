package net.chaos.chaosmod.jobs.reward;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayerMP;

public abstract class JobReward {
	protected int level;

	public JobReward(int level) {
		this.level = level;
	}

	public abstract JsonElement toJson();

	public static JobReward fromJson(JsonObject json) {
	    throw new UnsupportedOperationException("Call a concrete subclass fromJson instead");
	}

	public abstract void give(EntityPlayerMP player);

	public int getLevel() { return level; }
}