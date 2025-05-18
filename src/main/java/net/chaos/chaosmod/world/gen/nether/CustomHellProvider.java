package net.chaos.chaosmod.world.gen.nether;

import net.chaos.chaosmod.init.ModBiomes;
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
        this.biomeProvider = new BiomeProviderSingle(ModBiomes.CUSTOM_HELL);
        this.doesWaterVaporize = false;
        this.nether = true;
		// super.init();
	}

	@Override
    public BiomeProvider getBiomeProvider() {
        return new BiomeProviderSingle(ModBiomes.CUSTOM_HELL); // for full replacement
        // OR use your own BiomeProvider that returns multiple biomes based on x/z
    }
	
	@Override
	public IChunkGenerator createChunkGenerator() {
		// TODO Auto-generated method stub
        return new CustomChunkGeneratorHell(this.world, this.world.getWorldInfo().isMapFeaturesEnabled(), this.world.getSeed());
	}
    // Optional: Add custom sky, fog, etc.

}
