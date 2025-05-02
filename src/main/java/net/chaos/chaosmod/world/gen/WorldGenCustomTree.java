package net.chaos.chaosmod.world.gen;

import java.util.Random;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenCustomTree extends WorldGenAbstractTree {
    private final IBlockState log;
    private final IBlockState leaves;

    public WorldGenCustomTree(IBlockState log, IBlockState leaves) {
        super(false);
        this.log = log;
        this.leaves = leaves;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        int height = rand.nextInt(3) + 4; // Tree height
        boolean canGenerate = true;

        // Check if we can generate here (you may want to expand this with biome rules)
        for (int y = 0; y <= height + 1; y++) {
            BlockPos checkPos = position.up(y);
            if (!world.isAirBlock(checkPos) && !world.getBlockState(checkPos).getMaterial().isReplaceable() && world.getBlockState(position).getBlock() != ModBlocks.CUSTOM_LEAVES) {
                canGenerate = false;
                break;
            } else {
            	System.out.println("CANT GENERATE HERE : " + position + " Blockstate : " + world.getBlockState(position).getBlock());
            }
        }

        if (!canGenerate) return false;

        // Set logs
        for (int y = 0; y < height; y++) {
            world.setBlockState(position.up(y), log, 2);
        }

        // Set leaves
        for (int y = height - 2; y <= height; y++) {
            int radius = y == height ? 1 : 2;
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos leafPos = position.add(dx, y, dz);
                    if (Math.abs(dx) != radius || Math.abs(dz) != radius || rand.nextBoolean()) {
                        IBlockState state = world.getBlockState(leafPos);
                        if (state.getBlock().isAir(state, world, leafPos) || state.getMaterial().isReplaceable()) {
                            world.setBlockState(leafPos, leaves, 2);
                        }
                    }
                }
            }
        }

        return true;
    }
}