package net.chaos.chaosmod.biomes.gen;

import java.util.Random;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomLakes extends WorldGenerator {
	private Block block;
	private ChunkPos chunkPos;
	private final Predicate<IBlockState> predicate;

	public WorldGenCustomLakes(Block block) {
		this.block = block;
		this.predicate = new SandPredicate();
	}

	public void setGeneratedChunk(ChunkPos chunkPos) { this.chunkPos = chunkPos; }

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		int startX = chunkPos.getXStart();
		int endX = chunkPos.getXEnd();
		int startZ = chunkPos.getZStart();
		int endZ = chunkPos.getZEnd();

		for (int tries = 0; tries < 1; tries++) {
			if (rand.nextInt(20) != 0) break;
			int x = startX + rand.nextInt(16);
			int z = startZ + rand.nextInt(16);

			if (x > endX || z > endZ)
				continue;

			// FIXME : depth not working
			int r = 2 + rand.nextInt(2); // radius
			int depth = 5;
			for (int i = -r; i <= r; i++) {
				for (int j = -r; j <= r; j++) {
					for (int k = 0; k <= depth; k++) {
						BlockPos triedPos = new BlockPos(x, 0, z).add(i, 0, j);
						triedPos = worldIn.getHeight(triedPos).down(k);
						if (!worldIn.isBlockLoaded(triedPos, false))
							continue;

						IBlockState existing = worldIn.getBlockState(triedPos);
						if (!predicate.apply(existing))
							continue;

						if ((i != -r && i != r) && (j != -r && j != r) && k != depth) {
							worldIn.setBlockState(triedPos, block.getDefaultState(), 2 | 16);
						} else {
							if (rand.nextInt(10) > 3) {
								worldIn.setBlockState(triedPos, Blocks.STONE.getDefaultState(), 2 | 16);
							} else {
								worldIn.setBlockState(triedPos, Blocks.STONEBRICK.getDefaultState(), 2 | 16);
							}
						}
					}
				}
			}
		}

		return true;
	}

	static class SandPredicate implements Predicate<IBlockState> {
		private SandPredicate() {}

		public boolean apply(IBlockState state) {
			if (state != null && state.getBlock() == Blocks.SAND)
				return true;

			return false;
		}
	}
}