package net.chaos.chaosmod.world.gen.hive;

import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModDimensions;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderHell;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HiveWorldProvider extends WorldProviderHell {

	@Override
	public void init() {
		this.biomeProvider = new BiomeProviderSingle(ModBiomes.HIVE);
		this.nether = false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return new Vec3d(1.0D, 0.647D, 0.0D);
	}
	
    @SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int x, int z) {
		return super.doesXZShowFog(x, z);
	}
	
	@Override
	protected void generateLightBrightnessTable() {
        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.0F - (float)i / 15.0F; // f1 between [0.0f, 1.0f]
//            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 1.0F + 0.0F; // between [0.0f, 1.0f]
            this.lightBrightnessTable[i] = 1.0F;
        }
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorHive(this.world, this.world.getSeed());
	}

	@Override
	public DimensionType getDimensionType() {
		return ModDimensions.THE_HIVE;
	}
}