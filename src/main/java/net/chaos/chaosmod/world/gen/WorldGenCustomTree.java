package net.chaos.chaosmod.world.gen;

import java.util.Random;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenCustomTree extends WorldGenAbstractTree {
    private final IBlockState log;
    private final IBlockState leaves;
    private final IBlockState altar;

    public WorldGenCustomTree(IBlockState log, IBlockState leaves, IBlockState altar) {
        super(false);
        this.log = log;
        this.leaves = leaves;
		this.altar = altar;
    }

    @Override
    public boolean generate(World world, Random rand, BlockPos position) {
        int height = rand.nextInt(3) + 4; // Tree height
        boolean canGenerate = true;

        // Check if we can generate here
        for (int y = 0; y <= height + 1; y++) {
            BlockPos checkPos = position.up(y);
            if (!world.isAirBlock(checkPos) && !world.getBlockState(checkPos).getMaterial().isReplaceable() && 
            	 world.getBlockState(position).getBlock() != ModBlocks.CUSTOM_LEAVES &&
            	 world.getBlockState(position).getBlock() != ModBlocks.CUSTOM_STAIRS &&
            	 world.getBlockState(position).getBlock() != ModBlocks.OXONIUM_STAIRS &&
            	 !(world.getBlockState(position).getBlock() instanceof BlockBush) &&
            	 !(world.getBlockState(position).getBlock() instanceof BlockLeaves)) {
                canGenerate = false;
                break;
            }
        }

        if (!canGenerate) return false;


        // Set logs
        for (int y = 0; y < height; y++) {
            world.setBlockState(position.up(y), log, 2);
        }
        
        // Set lights
        if (rand.nextInt(4) == 2) {
        	EnumFacing facing_light = EnumFacing.HORIZONTALS[rand.nextInt(4)];
        	BlockPos light_pos = position.up(height - 3).offset(facing_light);
        	world.setBlockState(light_pos, ModBlocks.PINK_LANTERN.getStateForPlacement(world, light_pos, facing_light, light_pos.getX(), light_pos.getY(), light_pos.getZ(), 0, null, EnumHand.MAIN_HAND));
        }

        if (rand.nextInt(100) == 0) world.setBlockState(position.up(height + 1), altar, 2); // boss altar on top of it 1/100 chance

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