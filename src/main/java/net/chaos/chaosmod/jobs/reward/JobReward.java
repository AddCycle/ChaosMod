package net.chaos.chaosmod.jobs.reward;

import com.google.gson.JsonObject;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public abstract class JobReward {
	protected int level;
	protected int amount;

	public JobReward(int level, int amount) {
		this.level = level;
		this.amount = amount;
	}

	public abstract JsonObject toJson();

	public static JobReward fromJson(JsonObject json) {
		int level = json.get("level").getAsInt();
		JsonObject reward = json.get("reward").getAsJsonObject();
		int amount = reward.has("amount") ? reward.get("amount").getAsInt() : 1;

		if (reward.has("item")) {
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(reward.get("item").getAsString()));
			if (item == null) item = ModItems.MONEY_WAD;
			return new ItemReward(level, item, amount);
		}

		if (reward.has("block")) {
			Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(reward.get("block").getAsString()));
			if (block == null) return new ItemReward(level, ModItems.MONEY_WAD, amount);
			return new BlockReward(level, block, amount);
		}

		throw new IllegalArgumentException("Unknown jobs reward type");
	}

	public abstract void give(EntityPlayer player);
	public abstract String getName();

	public int getLevel() { return level; }

	public int getAmount() { return amount; }
}