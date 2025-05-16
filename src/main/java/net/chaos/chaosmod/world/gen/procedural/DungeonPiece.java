package net.chaos.chaosmod.world.gen.procedural;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class DungeonPiece {
	public BlockPos origin;
	public ResourceLocation file;
	public List<BlockPos> connectionPoints;
	
	public DungeonPiece(ResourceLocation file, BlockPos origin) {
        this.file = file;
        this.origin = origin;
        this.connectionPoints = new ArrayList<>();
    }

}
