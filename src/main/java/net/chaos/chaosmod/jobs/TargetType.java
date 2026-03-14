package net.chaos.chaosmod.jobs;

public enum TargetType {
	BLOCK(0), // harvest
	ENTITY(1), // kill
	ITEM(2); // craft
	
	public int id;

	TargetType(int id) {
		this.id = id;
	}

	static TargetType valueOf(int val) {
		switch (val) {
			case 0: return BLOCK;
			case 1: return ENTITY;
			case 2: return ITEM;
			default: throw new IllegalArgumentException("Unknown TargetType");
		}
	}
}