package net.chaos.chaosmod.blocks.decoration;

import net.minecraft.util.IStringSerializable;

public enum FlowerType implements IStringSerializable {
	SNOW_FLOWER(0, "snow_flower"),
	BURNING_FLOWER(1, "blazing_flower"),
	ENDER_FLOWER(2, "ender_flower"),
	THUNDER_FLOWER(3, "thunder_flower");

	private static final FlowerType[] META_LOOKUP = new FlowerType[values().length];
	private final int meta;
	private final String name;

	
	private FlowerType(int meta, String name) {
		this.meta = meta;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public int getMeta() {
		return meta;
	}

	public static FlowerType byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }
        return META_LOOKUP[meta];
    }

    static {
        for (FlowerType type : values()) {
            META_LOOKUP[type.getMeta()] = type;
        }
    }
}
