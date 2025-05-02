package net.chaos.chaosmod.world;

import java.util.Random;

import net.chaos.chaosmod.blocks.decoration.BlockCustomFlower;
import net.chaos.chaosmod.blocks.decoration.FlowerType;
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

    private void generateEnder(World world, Random random, int x, int z)
    {
    	this.generateOre(ModBlocks.ENDERITE_ORE, Blocks.END_STONE, world, random, 3, 5, x, z, 40, 80);
    }

    private void generateOverworld(World world, Random random, int x, int z)
    {
            if(random.nextInt(4) < 2)
            {
            	this.generateOre(ModBlocks.OXONIUM_ORE, Blocks.STONE, world, random, 5, 5, x, z, 3, 30);
            }
            else
            {
            	this.generateOre(ModBlocks.OXONIUM_ORE, Blocks.STONE, world, random, 3, 3, x, z, 3, 30);
            }
            
            generateFlowers(world, random, x, z);

    }

	private void generateNether(World world, Random random, int x, int z)
    {
            this.generateOre(ModBlocks.ALLEMANITE_ORE, Blocks.NETHERRACK, world, random, 4, 2, x, z, 3, 126);
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

    private void generateFlowers(World world, Random rand, int x, int z) {
    	FlowerType randomVariant = FlowerType.values()[rand.nextInt(FlowerType.values().length)];

    	// Set block state with the random variant
    	IBlockState flowerState = ModBlocks.CUSTOM_FLOWER.getDefaultState().withProperty(BlockCustomFlower.type, randomVariant);
        BlockPos pos = new BlockPos(x + rand.nextInt(16), 0, z + rand.nextInt(16));
        BlockPos topPos = world.getHeight(pos); // Get highest non-air block

        for (int i = 0; i < 4; i++) { // number of tries
            int offsetX = x + rand.nextInt(16);
            int offsetZ = z + rand.nextInt(16);
            int offsetY = topPos.getY();
            // int offsetY = rand.nextInt(topPos.getY());

            BlockPos flowerPos = new BlockPos(offsetX, offsetY, offsetZ);
            /* Biome specific gen
             * Biome biome = world.getBiome(flowerPos);
			 * if (biome instanceof BiomePlains && world.isAirBlock(flowerPos) && ModBlocks.MY_FLOWER.canPlaceBlockAt(world, flowerPos))
             */
            if (world.isAirBlock(flowerPos) && ModBlocks.CUSTOM_FLOWER.canPlaceBlockAt(world, flowerPos)) {
            	System.out.println("GENERATING PLANT AT : " + flowerPos + " | state : " + flowerState);
                world.setBlockState(flowerPos, flowerState, 2);
            }
        }
    }

}
