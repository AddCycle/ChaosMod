package net.chaos.chaosmod.blocks.abstracted;

import net.minecraft.util.IStringSerializable;

public enum WoodVariants implements IStringSerializable {
	SNOWY(0, "snowy"),
	MAPLE(1, "maple"),
	ENDER(2, "ender"),
	OLIVE(3, "olive");

	private static final WoodVariants[] META_LOOKUP = new WoodVariants[values().length];
	private final int meta;
	private final String name;

	WoodVariants(int meta, String name) {
		this.meta = meta;
		this.name = name;
	}

	public int getMeta() {
		return this.meta;
	}

	public static WoodVariants byMetadata(int meta) {
		if (meta < 0 || meta >= META_LOOKUP.length) {
			meta = 0;
		}
		return META_LOOKUP[meta];
	}

	@Override
	public String getName() {
		return this.name;
	}

	static {
		for (WoodVariants type : values()) {
			META_LOOKUP[type.getMeta()] = type;
		}
	}
}