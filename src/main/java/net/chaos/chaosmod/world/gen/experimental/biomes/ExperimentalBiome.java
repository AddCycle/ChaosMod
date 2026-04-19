package net.chaos.chaosmod.world.gen.experimental.biomes;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;

public class ExperimentalBiome extends Biome {

	public ExperimentalBiome() {
		super(new BiomeProperties("Experimental Biome")
				.setHeightVariation(0.05f)
				.setBaseHeight(0.125F)
				.setRainDisabled());
	}

	@Override
	public BiomeDecorator createBiomeDecorator() {
		return super.createBiomeDecorator();
	}
}