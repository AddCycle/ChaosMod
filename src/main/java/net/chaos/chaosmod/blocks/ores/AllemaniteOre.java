package net.chaos.chaosmod.blocks.ores;

import net.minecraft.block.material.Material;

public class AllemaniteOre extends AbstractOre {

	public AllemaniteOre(String name, Material material) {
		super(name, material);
		setHarvestLevel("pickaxe", 3);
		setLightLevel(17);
	}

	@Override
	public float getSmeltingTemperature() {
		return 1064 + 3 * 80;
	}
}