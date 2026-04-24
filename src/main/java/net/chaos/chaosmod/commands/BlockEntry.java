package net.chaos.chaosmod.commands;

import net.minecraft.block.Block;

public class BlockEntry {
	private Block block;
	private int weight;
	private int meta;

	public BlockEntry(Block block, int weight) {
		this.block = block;
		this.weight = weight;
		this.meta = 0;
	}

	public BlockEntry(Block block, int weight, int meta) {
		this.block = block;
		this.weight = weight;
		this.meta = meta;
	}

	public Block getBlock() { return block; }

	public int getWeight() { return weight; }
	
	public int getMeta() { return meta; }
}