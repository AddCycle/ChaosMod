package net.chaos.chaosmod.minimap;

public class ChunkKey {
    public final int chunkX;
    public final int chunkZ;

    public ChunkKey(int chunkX, int chunkZ) {
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChunkKey)) return false;
        ChunkKey other = (ChunkKey) obj;
        return this.chunkX == other.chunkX && this.chunkZ == other.chunkZ;
    }

    @Override
    public int hashCode() {
        return 31 * chunkX + chunkZ;
    }
}