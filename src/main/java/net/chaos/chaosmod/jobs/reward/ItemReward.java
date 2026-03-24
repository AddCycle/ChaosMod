package net.chaos.chaosmod.jobs.reward;

import com.google.gson.JsonObject;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemReward extends JobReward {
    private Item item;

    public ItemReward(int level, Item item, int amount) {
        super(level, amount);
        this.item = item;
    }

    @Override
    public void give(EntityPlayer player) {
        if (player.world.isRemote) return;

        Main.getLogger().debug("Giving {} x{} to {}", this.getName(), this.amount, player.getName());
        player.addItemStackToInventory(new ItemStack(this.item, this.amount));
    }

	@Override
	public String getName() {
		return item.getRegistryName().toString();
	}

	@Override
	public JsonObject toJson() {
        JsonObject obj = new JsonObject();

        JsonObject reward = new JsonObject();
        reward.addProperty("item", this.getName());
        reward.addProperty("amount", amount);

        obj.addProperty("level", level);
        obj.add("reward", reward);

        return obj;
	}
}