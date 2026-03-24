package net.chaos.chaosmod.jobs.reward;

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
        player.addItemStackToInventory(new ItemStack(item, amount));
    }

	@Override
	public String getName() {
		return item.getRegistryName().toString();
	}
}