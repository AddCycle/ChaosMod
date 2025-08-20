package net.chaos.chaosmod.common.capabilities;

public interface IMoney {
	public int get();
	public void set(int value);
	public void add(int amount);
}
