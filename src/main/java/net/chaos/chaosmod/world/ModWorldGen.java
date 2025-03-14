package net.chaos.chaosmod.world;

import java.util.Random;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ModWorldGen implements IWorldGenerator {
	/*
	 * Overworld = 0
	 * End = 1
	 * Nether = -1
	 */
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			generateOverworld(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 1:
			generateEnder(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}
	
	/*private void generateOverworld(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		this.generateOre(overworldGen, ModBlocks.OXONIUM_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 16, 64, random.nextInt(3) + 2, 16);
	}

	private void generateNether(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		this.generateOre(netherGen, ModBlocks.ALLEMANITE_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 0, 100, random.nextInt(2) + 2, 12);
	}

	private void generateEnder(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		this.generateOre(enderGen, ModBlocks.ENDERITE_ORE.getDefaultState(), world, random, chunkX * 16, chunkZ * 16, 0, 100, random.nextInt(1) + 1, 8);
	}*/

	/*private void generateOre(WorldGenerator generator, IBlockState ore, World world, Random random, int x, int z, int minY, int maxY, int size, int chances) {

		int deltaY = maxY - minY;
		for (int i = 0; i < chances; i++) {
			BlockPos pos = new BlockPos(x + random.nextInt(16), minY + random.nextInt(deltaY), z + random.nextInt(16));
			
			// WorldGenMinable generator = new WorldGenMinable(ore, size);
			generator.generate(world, random, pos);
		}
	}*/


    private void generateEnder(World world, Random random, int x, int z)
    {
    	this.generateOre(ModBlocks.ENDERITE_ORE, Blocks.END_STONE, world, random, 2, 2, x, z, 40, 60);
    }

    private void generateOverworld(World world, Random random, int x, int z)
    {
            if(random.nextInt(4) == 0)
            {
                    this.generateOre(ModBlocks.OXONIUM_ORE, Blocks.STONE, world, random, 5, 5, x, z, 3, 30);
            }
            else
            {
                    this.generateOre(ModBlocks.OXONIUM_ORE, Blocks.STONE, world, random, 3, 3, x, z, 3, 30);
            }

    }

    private void generateNether(World world, Random random, int x, int z)
    {
            this.generateOre(ModBlocks.ALLEMANITE_ORE, Blocks.NETHERRACK, world, random, 7, 2, x, z, 3, 126);
    }

    public void generateOre(Block ore, Block block, World world, Random random, int maxVeinSize, int chancesToSpawn, int x, int z, int minY, int maxY)
    {
        for(int i = 0; i < chancesToSpawn; i++)
        {
            assert maxY > minY;
                assert minY > 0;
                assert maxY < 256 && maxY > 0;

                int posY = random.nextInt(maxY - minY);

                WorldGenerator oreGen = new WorldGenMinable(ore.getDefaultState(), maxVeinSize, BlockMatcher.forBlock(block));
                oreGen.generate(world, random, new BlockPos(x + random.nextInt(16), minY + posY, z + random.nextInt(16)));
        }

    }

}
