package net.chaos.chaosmod.biomes.gen;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

/**
 * Can be used for BlockFlower as well as it's a subclass of BlockBush
 */
public class CustomWorldGenFlowers extends WorldGenerator {
    public BlockBush flower;
    public IBlockState state;

	public CustomWorldGenFlowers(BlockBush flowerIn) {
        this.setGeneratedBlock(flowerIn, flowerIn.getDefaultState());
	}

    public void setGeneratedBlock(BlockBush flowerIn)
    {
        this.setGeneratedBlock(flowerIn, flowerIn.getDefaultState());
    }
	
	public void setGeneratedBlock(BlockBush flowerIn, IBlockState state) {
		this.flower = flowerIn;
        this.state = state;
	}
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
        for (int i = 0; i < 64; ++i)
        {
            BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

            if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < 255) && this.flower.canBlockStay(worldIn, blockpos, this.state))
            {
                worldIn.setBlockState(blockpos, this.state, 2);
            }
        }

        return true;
	}

}
