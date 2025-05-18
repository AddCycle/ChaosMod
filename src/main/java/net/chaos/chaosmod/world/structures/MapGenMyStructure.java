package net.chaos.chaosmod.world.structures;

import java.util.Random;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenMyStructure extends MapGenStructure {

    public MapGenMyStructure() {
        this.structureMap = new Long2ObjectOpenHashMap<>();
    }

    @Override
    public String getStructureName() {
        return "MyStructure";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
        int spacing = 32;  // try spawning every 32 chunks
        int separation = 8;

        int i = chunkX / spacing;
        int j = chunkZ / spacing;

        Random rand = this.world.setRandomSeed(i, j, 10387313);
        i = i * spacing + rand.nextInt(spacing - separation);
        j = j * spacing + rand.nextInt(spacing - separation);

        return chunkX == i && chunkZ == j;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ) {
        return new StructureMyStructureStart(this.world, this.rand, chunkX, chunkZ);
    }

	@Override
	public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored) {
		// TODO Auto-generated method stub
		return null;
	}
}