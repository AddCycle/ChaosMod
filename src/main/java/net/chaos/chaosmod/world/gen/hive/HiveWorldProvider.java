package net.chaos.chaosmod.world.gen.hive;

import net.chaos.chaosmod.init.ModBiomes;
import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModDimensions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HiveWorldProvider extends WorldProvider {

	@Override
	public void init() {
		this.biomeProvider = new BiomeProviderSingle(ModBiomes.HIVE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(float celestialAngle, float partialTicks) {
		return new Vec3d(1.0D, 0.647D, 0.0D);
	}

	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int x, int z) { // TODO : don't show fog approaching structures
		return true;
	}

	@Override
	protected void generateLightBrightnessTable() {
        float f = 0.1F;

        for (int i = 0; i <= 15; ++i)
        {
            float f1 = 1.0F - (float)i / 15.0F;
            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
        }

//		for (int i = 0; i <= 15; ++i) {
//			float f1 = 1.0F - (float) i / 15.0F; // f1 between [0.0f, 1.0f]
////            this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 1.0F + 0.0F; // between [0.0f, 1.0f]
//			this.lightBrightnessTable[i] = 1.0F;
//		}
	}
	
	@Override
	public boolean isSurfaceWorld() { return false; }
	
	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return 0.5F;
	}
	
	@Override
	public boolean canRespawnHere() {
		return true;
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z) {
		BlockPos blockpos = new BlockPos(x, 0, z);

		if (this.world.getBiome(blockpos).ignorePlayerSpawnSuitability()) {
			return true;
		} else {
			return this.world.getGroundAboveSeaLevel(blockpos).getBlock() == ModBlocks.HONEYCOMB;
		}
	}

	@Override
	public int getAverageGroundLevel() {
		return super.getAverageGroundLevel();
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorHive(this.world, this.world.getSeed());
	}

	@Override
	public DimensionType getDimensionType() { return ModDimensions.THE_HIVE; }
}