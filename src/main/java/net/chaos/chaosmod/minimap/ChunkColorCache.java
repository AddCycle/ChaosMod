package net.chaos.chaosmod.minimap;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.world.World;

public class ChunkColorCache {
    private static final Map<ChunkKey, int[][]> cache = new HashMap<>();

    public static int[][] getChunkColors(World world, int chunkX, int chunkZ) {
        ChunkKey key = new ChunkKey(chunkX, chunkZ);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        int[][] colors = computeChunkColors(world, chunkX, chunkZ);
        cache.put(key, colors);
        return colors;
    }

    private static int[][] computeChunkColors(World world, int chunkX, int chunkZ) {
        int[][] result = new int[16][16];
        int worldX = chunkX << 4;
        int worldZ = chunkZ << 4;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                result[x][z] = Renderer.getBlockColor(world, worldX + x, worldZ + z);
            }
        }

        return result;
    }

    public static void invalidateChunk(int chunkX, int chunkZ) {
        cache.remove(new ChunkKey(chunkX, chunkZ));
    }

    public static void clear() {
        cache.clear();
    }
}