package net.chaos.chaosmod.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;

public class AllemaniteBlock extends BlockBase {

	public AllemaniteBlock(String name, Material material) {
		super(name, material);
		this.setLightLevel(0.2f);
		this.setResistance(15.0f);
		this.setHardness(5.0f);
	}
}