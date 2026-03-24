package net.chaos.chaosmod.jobs.reward;

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
		player.addItemStackToInventory(new ItemStack(block, amount));
	}

	@Override
	public String getName() {
		return block.getRegistryName().toString();
	}
}