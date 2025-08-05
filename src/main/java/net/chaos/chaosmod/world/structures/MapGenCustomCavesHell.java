package net.chaos.chaosmod.world.structures;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenCavesHell;

public class MapGenCustomCavesHell extends MapGenCavesHell {

	protected static final IBlockState AIR = Blocks.AIR.getDefaultState();

	protected void addRoom(long seed, int chunkX, int chunkZ, ChunkPrimer primer, double x, double y, double z)
	{
		// Increase min tunnel size, e.g. from 1.0F to 2.0F minimum radius
		this.addTunnel(seed, chunkX, chunkZ, primer, x, y, z, 2.0F + this.rand.nextFloat() * 8.0F, 0.0F, 0.0F, -1, -1, 0.5D);
	}

	// actually x,y,z are originX, originY, originZ
	protected void addTunnel(long seed, int chunkX, int chunkZ, ChunkPrimer primer, double x, double y, double z, float horizontalAngle, float verticalAngle, float thickness, int currentStep, int maxSteps, double verticalScale)
	{
		double d0 = (double)(chunkX * 16 + 8);
		double d1 = (double)(chunkZ * 16 + 8);
		float f = 0.0F;
		float f1 = 0.0F;
		Random random = new Random(seed);

		if (maxSteps <= 0)
		{
			int i = this.range * 16 - 16;
			maxSteps = i - random.nextInt(i / 4);
		}

		boolean flag1 = false;

		if (currentStep == -1)
		{
			currentStep = maxSteps / 2;
			flag1 = true;
		}

		int j = random.nextInt(maxSteps / 2) + maxSteps / 4;

		for (boolean flag = random.nextInt(6) == 0; currentStep < maxSteps; ++currentStep)
		{
			double d2 = 1.5D + (double)(MathHelper.sin((float)currentStep * (float)Math.PI / (float)maxSteps) * horizontalAngle);
			double d3 = d2 * verticalScale;
			float f2 = MathHelper.cos(thickness);
			float f3 = MathHelper.sin(thickness);
			x += (double)(MathHelper.cos(verticalAngle) * f2);
			y += (double)f3;
			z += (double)(MathHelper.sin(verticalAngle) * f2);

			if (flag)
			{
				thickness = thickness * 0.92F;
			}
			else
			{
				thickness = thickness * 0.7F;
			}

			thickness = thickness + f1 * 0.1F;
			verticalAngle += f * 0.1F;
			f1 = f1 * 0.9F;
			f = f * 0.75F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (!flag1 && currentStep == j && horizontalAngle > 1.0F)
			{
				this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, x, y, z, random.nextFloat() * 0.5F + 0.5F, verticalAngle - ((float)Math.PI / 2F), thickness / 3.0F, currentStep, maxSteps, 1.0D);
				this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, x, y, z, random.nextFloat() * 0.5F + 0.5F, verticalAngle + ((float)Math.PI / 2F), thickness / 3.0F, currentStep, maxSteps, 1.0D);

				// FIXME : modify if those is not good :
				//                this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, x, y, z, random.nextFloat() * 0.5F + 0.5F, horizontalAngle - ((float)Math.PI / 2F), thickness / 3.0F, currentStep, maxSteps, 1.0D);
				//                this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, x, y, z, random.nextFloat() * 0.5F + 0.5F, horizontalAngle + ((float)Math.PI / 2F), thickness / 3.0F, currentStep, maxSteps, 1.0D);

				return;
			}

			if (flag1 || random.nextInt(4) != 0)
			{
				double d4 = x - d0;
				double d5 = z - d1;
				double d6 = (double)(maxSteps - currentStep);
				double d7 = (double)(horizontalAngle + 2.0F + 16.0F);

				if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7)
				{
					return;
				}

				if (x >= d0 - 16.0D - d2 * 2.0D && z >= d1 - 16.0D - d2 * 2.0D && x <= d0 + 16.0D + d2 * 2.0D && z <= d1 + 16.0D + d2 * 2.0D)
				{
					int j2 = MathHelper.floor(x - d2) - chunkX * 16 - 1;
					int k = MathHelper.floor(x + d2) - chunkX * 16 + 1;
					int k2 = MathHelper.floor(y - d3) - 1;
					int l = MathHelper.floor(y + d3) + 1;
					int l2 = MathHelper.floor(z - d2) - chunkZ * 16 - 1;
					int i1 = MathHelper.floor(z + d2) - chunkZ * 16 + 1;

					if (j2 < 0)
					{
						j2 = 0;
					}

					if (k > 16)
					{
						k = 16;
					}

					if (k2 < 1)
					{
						k2 = 1;
					}

					if (l > 120)
					{
						l = 120;
					}

					if (l2 < 0)
					{
						l2 = 0;
					}

					if (i1 > 16)
					{
						i1 = 16;
					}

					boolean flag2 = false;

					for (int j1 = j2; !flag2 && j1 < k; ++j1)
					{
						for (int k1 = l2; !flag2 && k1 < i1; ++k1)
						{
							for (int l1 = l + 1; !flag2 && l1 >= k2 - 1; --l1)
							{
								if (l1 >= 0 && l1 < 128)
								{
									IBlockState iblockstate = primer.getBlockState(j1, l1, k1);

									if (iblockstate.getBlock() == Blocks.FLOWING_LAVA || iblockstate.getBlock() == Blocks.LAVA)
									{
										flag2 = true;
									}

									if (l1 != k2 - 1 && j1 != j2 && j1 != k - 1 && k1 != l2 && k1 != i1 - 1)
									{
										l1 = k2;
									}
								}
							}
						}
					}

					if (!flag2)
					{
						for (int i3 = j2; i3 < k; ++i3)
						{
							double d10 = ((double)(i3 + chunkX * 16) + 0.5D - x) / d2;

							for (int j3 = l2; j3 < i1; ++j3)
							{
								double d8 = ((double)(j3 + chunkZ * 16) + 0.5D - z) / d2;

								for (int i2 = l; i2 > k2; --i2)
								{
									double d9 = ((double)(i2 - 1) + 0.5D - y) / d3;

									if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D)
									{
										IBlockState iblockstate1 = primer.getBlockState(i3, i2, j3);

										if (iblockstate1.getBlock() == Blocks.NETHERRACK || iblockstate1.getBlock() == Blocks.DIRT || iblockstate1.getBlock() == Blocks.GRASS)
										{
											// 1. Carve air first
										    primer.setBlockState(i3, i2, j3, Blocks.AIR.getDefaultState());

										    // 2. For each neighbor block, if it is NETHERRACK, replace with nether bricks
										    for (EnumFacing face : EnumFacing.values()) {
										        int nx = i3 + face.getFrontOffsetX();
										        int ny = i2 + face.getFrontOffsetY();
										        int nz = j3 + face.getFrontOffsetZ();

										        if (nx >= 0 && nx < 16 && ny >= 1 && ny < 128 && nz >= 0 && nz < 16) {
										            IBlockState neighborBlock = primer.getBlockState(nx, ny, nz);
										            if (neighborBlock.getBlock() == Blocks.NETHERRACK) {
										                primer.setBlockState(nx, ny, nz, Blocks.NETHER_BRICK.getDefaultState());
										            }
										        }
										    }
										}
									}
								}
							}
						}

						if (flag1)
						{
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Recursively called by generate()
	 */
	protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ, ChunkPrimer chunkPrimerIn)
	{
		int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);

		if (this.rand.nextInt(5) != 0)
		{
			i = 0;
		}

		for (int j = 0; j < i; ++j)
		{
			double d0 = (double)(chunkX * 16 + this.rand.nextInt(16));
			double d1 = (double)this.rand.nextInt(128);
			double d2 = (double)(chunkZ * 16 + this.rand.nextInt(16));
			int k = 1;

			if (this.rand.nextInt(4) == 0)
			{
				this.addRoom(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, d0, d1, d2);
				k += this.rand.nextInt(4);
			}

			for (int l = 0; l < k; ++l)
			{
				float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
				float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float f2 = this.rand.nextFloat() * 3.0F + this.rand.nextFloat() * 2.0F;  // Bigger thickness
				this.addTunnel(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, d0, d1, d2, f2 * 2.0F, f, f1, 0, 0, 0.5D);
			}
		}
	}
}
