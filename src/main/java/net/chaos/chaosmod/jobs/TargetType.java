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
		if (val == 0) {
			return BLOCK;
		}
		return val == 1 ? ENTITY : ITEM;
	}
}
