package net.chaos.chaosmod.jobs.reward;

import com.google.gson.JsonObject;

import net.chaos.chaosmod.Main;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class BlockReward extends JobReward {
	private Block block;

	public BlockReward(int level, Block block, int amount) {
		super(level, amount);
		this.block = block;
	}

	@Override
	public void give(EntityPlayer player) {
		if (player.world.isRemote) return;

        Main.getLogger().debug("Giving {} x{} to {}", this.getName(), this.amount, player.getName());
		player.addItemStackToInventory(new ItemStack(block, amount));
	}

	@Override
	public String getName() {
		return block.getRegistryName().toString();
	}

	@Override
	public JsonObject toJson() {
        JsonObject obj = new JsonObject();

        JsonObject reward = new JsonObject();
        reward.addProperty("block", this.getName());
        reward.addProperty("amount", amount);

        obj.addProperty("level", level);
        obj.add("reward", reward);

        return obj;
	}
}