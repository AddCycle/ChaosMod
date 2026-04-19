package net.chaos.chaosmod.world.gen.nether;

import net.minecraft.init.Biomes;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;

public class CustomHellProvider extends WorldProviderHell {

	/*public CustomHellProvider() {
		this.doesWaterVaporize = false;
	}*/
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
        // this.biomeProvider = new BiomeProviderSingle(ModBiomes.NETHER_CAVES);
        this.doesWaterVaporize = false;
        this.nether = true;
		// super.init();
	}

	@Override
    public BiomeProvider getBiomeProvider() {
        return new BiomeProviderSingle(Biomes.HELL);
    }
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		// TODO Auto-generated method stub
        return new CustomChunkGeneratorHell(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed());
	}
}