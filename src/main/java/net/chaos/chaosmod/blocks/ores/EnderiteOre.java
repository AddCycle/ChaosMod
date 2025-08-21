package net.chaos.chaosmod.blocks.ores;

import net.minecraft.block.material.Material;

public class EnderiteOre extends AbstractOre {

	public EnderiteOre(String name, Material material) {
		super(name, material);
		setHarvestLevel("pickaxe", 4);
		setLightLevel(17);
	}

	@Override
	public float getSmeltingTemperature() {
		return 1064 + 4 * 80;
	}
}