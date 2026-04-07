package net.chaos.chaosmod.jobs;

public enum TargetType {
	BLOCK(0),
	ENTITY(1),
	ITEM(2);
	
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