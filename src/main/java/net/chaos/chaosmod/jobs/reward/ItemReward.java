package net.chaos.chaosmod.jobs.reward;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ItemReward extends JobReward {
	private List<ItemStack> stacks;

	public ItemReward(int level, List<ItemStack> stacks) {
		super(level);
		this.stacks = stacks;
	}

	public ItemReward(int level, ItemStack... stacks) {
		super(level);
		this.stacks = Arrays.asList(stacks);
	}

	@Override
	public void give(EntityPlayerMP player) {

		stacks.forEach(stack ->
		{
			player.addItemStackToInventory(stack);
			Main.getLogger().debug("Giving {} x{} to {}", stack.getItem().getRegistryName().toString(),
					stack.getCount(), player.getName());
		});
	}

	// FIXME : refactor hard reuse of existing code
	public static JobReward fromJson(JsonObject json) {
		int level = json.get("level").getAsInt();
		List<ItemStack> stacksList = new ArrayList<>();

		if (json.has("reward")) {
			JsonObject singleReward = json.get("reward").getAsJsonObject();
			int lvl = json.get("level").getAsInt();
		    int mnt = singleReward.get("amount").getAsInt();

			if (singleReward.has("item")) {
				Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(singleReward.get("item").getAsString()));
				if (item == null) item = ModItems.MONEY_WAD; // defaults to money_wad
				return new ItemReward(lvl, new ItemStack(item, mnt));
			}

			if (singleReward.has("block")) {
				Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(singleReward.get("block").getAsString()));
				if (block == null) {
					return new ItemReward(lvl, new ItemStack(ModItems.MONEY_WAD, mnt)); // idem
				}
				return new ItemReward(lvl, new ItemStack(block, mnt));
			}

			return new ItemReward(lvl, new ItemStack(ModItems.MONEY_WAD, mnt));
		}
		
		JsonArray rewards = json.get("rewards").getAsJsonArray();
		for (JsonElement r : rewards) {
			JsonObject reward = r.getAsJsonObject();
			int amount = reward.has("amount") ? reward.get("amount").getAsInt() : 1;

			if (reward.has("item")) {
				Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(reward.get("item").getAsString()));
				if (item == null) item = ModItems.MONEY_WAD; // defaults to money_wad
				stacksList.add(new ItemStack(item, amount));
			}

			if (reward.has("block")) {
				Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(reward.get("block").getAsString()));
				if (block == null) {
					stacksList.add(new ItemStack(ModItems.MONEY_WAD, amount)); // idem
				} else {
					stacksList.add(new ItemStack(block, amount));
				}
			}
		}
		
		return new ItemReward(level, stacksList);
	}

	@Override
	public JsonElement toJson() {
		JsonObject rewardObj = new JsonObject();

		rewardObj.addProperty("level", level);

		JsonArray rewards = new JsonArray();
		for (ItemStack stack : stacks) {
			JsonObject reward = new JsonObject();
			reward.addProperty("item", stack.getItem().getRegistryName().toString());
			reward.addProperty("amount", stack.getCount());

			rewards.add(reward);
		}

		rewardObj.add("rewards", rewards);
		return rewardObj; // rewards: []
	}
}