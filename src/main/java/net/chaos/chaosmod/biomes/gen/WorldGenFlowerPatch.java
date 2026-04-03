package net.chaos.chaosmod.biomes.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class WorldGenFlowerPatch extends AbstractSafeWorldGenerator {

	public WorldGenFlowerPatch(Block flowerIn) {
        this.setGeneratedBlock(flowerIn.getDefaultState());
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		int startX = chunkPos.getXStart();
		int endX = chunkPos.getXEnd();

		int startZ = chunkPos.getZStart();
		int endZ = chunkPos.getZEnd();
		
		Chunk chunk = worldIn.getChunkFromChunkCoords(chunkPos.x, chunkPos.z);

		for (int i = 0; i < 32; ++i) {
			if (rand.nextInt(20) != 0) continue;

			int x = startX + rand.nextInt(16);
			int z = startZ + rand.nextInt(16);
			if (x > endX || z > endZ) continue;
			
			int localX = x & 15;
		    int localZ = z & 15;

		    int y = chunk.getHeightValue(localX, localZ);
		    if (y <= 0) continue;

			BlockPos blockpos = new BlockPos(x, y, z);

			BlockPos down = blockpos.down();
			IBlockState downState = chunk.getBlockState(localX, y - 1, localZ);

			IBlockState stateAt = chunk.getBlockState(localX, y, localZ);
			boolean isAir = stateAt.getBlock() == Blocks.AIR;

			if (isAir && canSustainPlant(downState, worldIn, down)) {
			    worldIn.setBlockState(blockpos, this.state, 2 | 16);
			}
		}

		return true;
	}
}