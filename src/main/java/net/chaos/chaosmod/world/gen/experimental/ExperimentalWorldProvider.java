package net.chaos.chaosmod.world.gen.experimental;

import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModDimensions;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class ExperimentalWorldProvider extends WorldProvider {

	public ExperimentalWorldProvider() {}
	
	@Override
	protected void init() {
		this.hasSkyLight = true;
		this.biomeProvider = new BiomeProviderSingle(ModBiomes.EXPERIMENTAL);
	}

	@Override
	public BiomeProvider getBiomeProvider() {
		return super.getBiomeProvider();
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ExperimentalChunkGenerator(world, getSeed());
//		return new SimpleChunkGenerator(world, getSeed());
	}

	@Override
	public DimensionType getDimensionType() { return ModDimensions.EXPERIMENTAL; }
}