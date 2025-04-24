package net.chaos.chaosmod.world.gen;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import util.Reference;

public class WorldGenCustomDungeon implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) {
		if (world.provider.getDimension() == 0) { // Only in Overworld
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            int y = world.getHeight(x, z);

            if (random.nextInt(100) < 5) { // 5% chance per chunk
                generateDungeon(world, new BlockPos(x, y, z));
            }
        }
	}
	
	private void generateDungeon(World world, BlockPos pos) {
        // Simple 3x3x3 stone brick cube with chest inside
        for (int dx = 0; dx < 3; dx++) {
            for (int dz = 0; dz < 3; dz++) {
                for (int dy = 0; dy < 3; dy++) {
                    world.setBlockState(pos.add(dx, dy, dz), Blocks.STONEBRICK.getDefaultState());
                }
            }
        }

        // Clear space inside
        for (int dx = 1; dx < 2; dx++) {
            for (int dz = 1; dz < 2; dz++) {
                for (int dy = 1; dy < 2; dy++) {
                    world.setBlockToAir(pos.add(dx, dy, dz));
                }
            }
        }

        // Place chest with loot
        BlockPos chestPos = pos.add(1, 1, 1);
        world.setBlockState(chestPos, Blocks.CHEST.getDefaultState());
        TileEntity tile = world.getTileEntity(chestPos);
        if (tile instanceof TileEntityChest) {
            ((TileEntityChest) tile).setLootTable(new ResourceLocation(Reference.MODID, "dungeon_loot"), world.rand.nextLong());
            // ((TileEntityChest) tile).setLootTable(new ResourceLocation("minecraft:chests/simple_dungeon"), world.rand.nextLong());
        }
    }

}
