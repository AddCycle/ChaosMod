package net.chaos.chaosmod.biomes.gen;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class AbstractSafeWorldGenerator extends WorldGenerator {
	protected ChunkPos chunkPos;
	protected IBlockState state;
	
	public void setGeneratedBlock(IBlockState state) {
		this.state = state;
	}
	
	public void setGeneratedChunk(ChunkPos chunkPos) {
		this.chunkPos = chunkPos;
	}

	protected boolean canSustainPlant(IBlockState state, World worldIn, BlockPos downPos) {
        return state.getBlock() == Blocks.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock() == Blocks.FARMLAND;
	}
}