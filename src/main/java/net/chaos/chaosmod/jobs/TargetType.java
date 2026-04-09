package net.chaos.chaosmod.jobs;

public enum TargetType {
	BLOCK(0),
	ENTITY(1),
	ITEM(2),
	BIOME(3),
	POTION(4);
	
	public int id;

	TargetType(int id) {
		this.id = id;
	}

	static TargetType valueOf(int val) {
		switch (val) {
			case 0: return BLOCK;
			case 1: return ENTITY;
			case 2: return ITEM;
			case 3: return BIOME;
			case 4: return POTION;
			default: throw new IllegalArgumentException("Unknown TargetType");
		}
	}
}