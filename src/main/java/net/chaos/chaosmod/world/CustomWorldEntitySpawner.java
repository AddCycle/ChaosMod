package net.chaos.chaosmod.world;

import java.util.List;
import java.util.Random;

import net.chaos.chaosmod.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

/**
 * Adapted from {@link CustomWorldEntitySpawner#performWorldGenSpawning(World, Biome, int, int, int, int, Random)} in order to spawn creatures inside non-surface worlds
 */
public class CustomWorldEntitySpawner {

    /**
     * Called during chunk generation to spawn initial creatures.
     *  
     * @param centerX The X coordinate of the point to spawn mobs arround.
     * @param centerZ The Z coordinate of the point to spawn mobs arround.
     * @param diameterX The X diameter of the rectangle to spawn mobs in
     * @param diameterZ The Z diameter of the rectangle to spawn mobs in
     */
    public static void performWorldGenSpawning(World worldIn, Biome biomeIn, int centerX, int centerZ, int diameterX, int diameterZ, Random randomIn)
    {
        List<Biome.SpawnListEntry> list = biomeIn.getSpawnableList(EnumCreatureType.CREATURE);

        if (!list.isEmpty())
        {
            while (randomIn.nextFloat() < biomeIn.getSpawningChance())
            {
                Biome.SpawnListEntry biome$spawnlistentry = (Biome.SpawnListEntry)WeightedRandom.getRandomItem(worldIn.rand, list);
                int i = biome$spawnlistentry.minGroupCount + randomIn.nextInt(1 + biome$spawnlistentry.maxGroupCount - biome$spawnlistentry.minGroupCount);
                IEntityLivingData ientitylivingdata = null;
                int j = centerX + randomIn.nextInt(diameterX);
                int k = centerZ + randomIn.nextInt(diameterZ);
                int l = j;
                int i1 = k;

                for (int j1 = 0; j1 < i; ++j1)
                {
                    boolean flag = false;

                    for (int k1 = 0; !flag && k1 < 4; ++k1)
                    {
                    	BlockPos top = worldIn.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k));
                    	BlockPos blockpos = top;

                    	while (top.getY() > 1)
                    	{
                    	    BlockPos below = top.down();

                    	    IBlockState floor = worldIn.getBlockState(below);
                    	    IBlockState body = worldIn.getBlockState(top);

                    	    if (floor.getBlock() == ModBlocks.HONEYCOMB
                    	        && body.getMaterial() == Material.AIR)
                    	    {
                    	        break;
                    	    }

                    	    top = below;
                    	}

                    	blockpos = top;
                        
//                        Main.getLogger().debug(
//                        	    "spawn block={} y={}",
//                        	    worldIn.getBlockState(blockpos.down()).getBlock(),
//                        	    blockpos.getY()
//                        	);

//                        if (CustomWorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, worldIn, blockpos))
                        if (CustomWorldEntitySpawner.canCreatureTypeSpawnBody(EntityLiving.SpawnPlacementType.ON_GROUND, worldIn, blockpos))
//                        if (true)
                        {
                            EntityLiving entityliving;

                            try
                            {
                                entityliving = biome$spawnlistentry.newInstance(worldIn);
                            }
                            catch (Exception exception)
                            {
                                exception.printStackTrace();
                                continue;
                            }

                            if (net.minecraftforge.event.ForgeEventFactory.canEntitySpawn(entityliving, worldIn, j + 0.5f, (float) blockpos.getY(), k +0.5f, false) == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) continue;
                            entityliving.setLocationAndAngles((double)((float)j + 0.5F), (double)blockpos.getY(), (double)((float)k + 0.5F), randomIn.nextFloat() * 360.0F, 0.0F);
                            worldIn.spawnEntity(entityliving);
                            ientitylivingdata = entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(entityliving)), ientitylivingdata);
                            flag = true;
                        }

                        j += randomIn.nextInt(5) - randomIn.nextInt(5);

                        for (k += randomIn.nextInt(5) - randomIn.nextInt(5); j < centerX || j >= centerX + diameterX || k < centerZ || k >= centerZ + diameterX; k = i1 + randomIn.nextInt(5) - randomIn.nextInt(5))
                        {
                            j = l + randomIn.nextInt(5) - randomIn.nextInt(5);
                        }
                    }
                }
            }
        }
    }
    
    public static boolean isValidEmptySpawnBlock(IBlockState state)
    {
        if (state.isBlockNormalCube())
        {
//        	Main.getLogger().debug("isValidEmptySpawnBlock fail to normal cube");
            return false;
        }
        else if (state.canProvidePower())
        {
//        	Main.getLogger().debug("isValidEmptySpawnBlock fail to provide power");
            return false;
        }
        else if (state.getMaterial().isLiquid())
        {
//        	Main.getLogger().debug("isValidEmptySpawnBlock fail to isLiquid");
            return false;
        }
        else
        {
        	boolean result = !BlockRailBase.isRailBlock(state);
//        	Main.getLogger().debug("isValidEmptySpawnBlock result: {}", result);
            return result;
        }
    }

    public static boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType spawnPlacementTypeIn, World worldIn, BlockPos pos)
    {
        if (!worldIn.getWorldBorder().contains(pos))
        {
            return false;
        }
        else
        {
//        	Main.getLogger().debug("canCreatureTypeSpawnAtLocation success");
            return spawnPlacementTypeIn.canSpawnAt(worldIn, pos);
        }
    }

    public static boolean canCreatureTypeSpawnBody(EntityLiving.SpawnPlacementType spawnPlacementTypeIn, World worldIn, BlockPos pos)
    {
        {
            IBlockState iblockstate = worldIn.getBlockState(pos);

            if (spawnPlacementTypeIn == EntityLiving.SpawnPlacementType.IN_WATER)
            {
                return iblockstate.getMaterial() == Material.WATER && worldIn.getBlockState(pos.down()).getMaterial() == Material.WATER && !worldIn.getBlockState(pos.up()).isNormalCube();
            }
            else
            {
                BlockPos blockpos = pos.down();
                IBlockState state = worldIn.getBlockState(blockpos);

                if (!state.getBlock().canCreatureSpawn(state, worldIn, blockpos, spawnPlacementTypeIn))
                {
                    return false;
                }
                else
                {
                    Block block = worldIn.getBlockState(blockpos).getBlock();
                    boolean flag = block != Blocks.BEDROCK && block != Blocks.BARRIER;
                    boolean result = flag && isValidEmptySpawnBlock(iblockstate) && isValidEmptySpawnBlock(worldIn.getBlockState(pos.up()));
//                    Main.getLogger().debug("canCreatureTypeSpawnBody : {}", result);
                    return result;
                }
            }
        }
    }
}