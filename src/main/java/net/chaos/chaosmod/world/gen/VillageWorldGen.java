package net.chaos.chaosmod.world.gen;

import java.util.Random;

import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.fml.common.IWorldGenerator;

public class VillageWorldGen implements IWorldGenerator {
    private MapGenCustomVillage customVillage = new MapGenCustomVillage();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
                         IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
    	// TODO : Remove if not used
        if (!world.isRemote && world.provider.getDimension() == 0) { // Overworld only
            customVillage.generateStructure(world, random, new ChunkPos(chunkX, chunkZ));
        }
    }
}