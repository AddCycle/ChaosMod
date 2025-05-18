package net.chaos.chaosmod.world.structures;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

public class StructureMyStructureStart extends StructureStart {

    public StructureMyStructureStart() {}

    public StructureMyStructureStart(World worldIn, Random rand, int chunkX, int chunkZ) {
        super(chunkX, chunkZ);

        // Calculate world coordinates for this chunk
        int x = chunkX * 16;
        int z = chunkZ * 16;

        // Add the first component (e.g. a room)
        this.components.add(new StructureMyRoom(worldIn, rand, new BlockPos(x, 64, z), null));

        // Optionally add linked hallways/components from this first piece
        this.components.get(0).buildComponent(this.components.get(0), this.components, rand);

        this.updateBoundingBox();
    }
}