package net.chaos.chaosmod.blocks.ores;
import net.minecraft.block.material.Material;

public class OxoniumOre extends AbstractOre {

	public OxoniumOre(String name, Material material) {
		super(name, material);
		setHarvestLevel("pickaxe", 2);
	}

	@Override
	public float getSmeltingTemperature() {
		return 1064; // slightly more than gold
	}
}