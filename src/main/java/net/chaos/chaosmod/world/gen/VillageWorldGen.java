package net.chaos.chaosmod.world.gen;

import java.util.Random;

import net.chaos.chaosmod.world.structures.MapGenCustomVillage;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.MapGenVillage;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.common.IWorldGenerator;

public class VillageWorldGen implements IWorldGenerator {
    private MapGenCustomVillage customVillage = new MapGenCustomVillage();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world,
                         IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.getDimension() == 0) {
            customVillage.generateStructure(world, random, new ChunkPos(chunkX, chunkZ));
            System.out.println("generating at : " + (chunkX << 4) + 8 + "|" + (chunkZ << 4) + 8);
        }
    }
}