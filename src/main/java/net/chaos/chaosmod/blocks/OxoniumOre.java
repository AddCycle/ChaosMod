package net.chaos.chaosmod.blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class OxoniumOre extends BlockBase {

	public OxoniumOre(String name, Material material) {
		super(name, material);
		setSoundType(SoundType.STONE);
		setHardness(5.0f);
		setResistance(15.0f);
		setHarvestLevel("pickaxe", 2);
	}
}