package net.chaos.chaosmod.common.capabilities.money;

public interface IMoney {
	public int get();
	public void set(int value);
	public void add(int amount);
}