package net.chaos.chaosmod.world.structures;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

/**
 * This class doesn't add anything for now just for learning and infomative purposes
 */
public class MapGenCustomCavesHell extends MapGenBase {
	protected static final IBlockState AIR = Blocks.AIR.getDefaultState();

	/**
	 * This function creates a flat oval cavern, almost every part of the nether
	 */
	protected void addRoom(long seed, int chunkX, int chunkZ, ChunkPrimer primer,
			double originX, double originY, double originZ) {
		this.addTunnel(seed, chunkX, chunkZ, primer, originX, originY, originZ,
				1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
	}

	/**
	 * @param seed 			RNG seed for this tunnel
	 * @param chunkX 		Chunk being generated (X)
	 * @param chunkZ 		Chunk being generated (Z)
	 * @param primer 		Block data to carve into
	 * @param originX 		Tunnel start position (world X)
	 * @param originY 		Tunnel start position (world Y)
	 * @param originZ 		Tunnel start position (world Z)
     * @param radius 		Base horizontal/vertical radius of the tunnel
     * @param yawAngle 		Horizontal direction the tunnel travels (left/right)
     * @param pitchAngle 	Vertical direction the tunnel travels (up/down)
     * @param currentStep 	Current iteration step (-1 = start from middle)
     * @param totalSteps 	Total length of the tunnel in steps
     * @param verticalScale Vertical squish factor (0.5 = flat oval, 1.0 = round)
	 */
	protected void addTunnel(long seed, int chunkX, int chunkZ, ChunkPrimer primer,
			double originX, double originY, double originZ, float radius, float yawAngle,
			float pitchAngle, int currentStep, int totalSteps, double verticalScale) {
		double d0 = (double) (chunkX * 16 + 8);
		double d1 = (double) (chunkZ * 16 + 8);
		float f = 0.0F;
		float f1 = 0.0F;
		Random random = new Random(seed);

		if (totalSteps <= 0) {
			int i = this.range * 16 - 16;
			totalSteps = i - random.nextInt(i / 4);
		}

		boolean flag1 = false;

		if (currentStep == -1) {
			currentStep = totalSteps / 2;
			flag1 = true;
		}

		int j = random.nextInt(totalSteps / 2) + totalSteps / 4;

		for (boolean flag = random.nextInt(6) == 0; currentStep < totalSteps; ++currentStep) {
			double horizRadius = 1.5D + (double) (MathHelper.sin((float) currentStep * (float) Math.PI / (float) totalSteps)
					* radius);
			double vertRadius = horizRadius * verticalScale;
			float f2 = MathHelper.cos(pitchAngle);
			float f3 = MathHelper.sin(pitchAngle);
			originX += (double) (MathHelper.cos(yawAngle) * f2);
			originY += (double) f3;
			originZ += (double) (MathHelper.sin(yawAngle) * f2);

			if (flag) {
				pitchAngle = pitchAngle * 0.92F;
			} else {
				pitchAngle = pitchAngle * 0.7F;
			}

			pitchAngle = pitchAngle + f1 * 0.1F;
			yawAngle += f * 0.1F;
			f1 = f1 * 0.9F;
			f = f * 0.75F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
			f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

			if (!flag1 && currentStep == j && radius > 1.0F) {
				this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, originX, originY,
						originZ, random.nextFloat() * 0.5F + 0.5F, yawAngle - ((float) Math.PI / 2F),
						pitchAngle / 3.0F, currentStep, totalSteps, 1.0D);
				this.addTunnel(random.nextLong(), chunkX, chunkZ, primer, originX, originY,
						originZ, random.nextFloat() * 0.5F + 0.5F, yawAngle + ((float) Math.PI / 2F),
						pitchAngle / 3.0F, currentStep, totalSteps, 1.0D);
				return;
			}

			if (flag1 || random.nextInt(4) != 0) {
				double d4 = originX - d0;
				double d5 = originZ - d1;
				double d6 = (double) (totalSteps - currentStep);
				double d7 = (double) (radius + 2.0F + 16.0F);

				if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7) {
					return;
				}

				if (originX >= d0 - 16.0D - horizRadius * 2.0D && originZ >= d1 - 16.0D - horizRadius * 2.0D
						&& originX <= d0 + 16.0D + horizRadius * 2.0D && originZ <= d1 + 16.0D + horizRadius * 2.0D) {
					int j2 = MathHelper.floor(originX - horizRadius) - chunkX * 16 - 1;
					int k = MathHelper.floor(originX + horizRadius) - chunkX * 16 + 1;
					int k2 = MathHelper.floor(originY - vertRadius) - 1;
					int l = MathHelper.floor(originY + vertRadius) + 1;
					int l2 = MathHelper.floor(originZ - horizRadius) - chunkZ * 16 - 1;
					int i1 = MathHelper.floor(originZ + horizRadius) - chunkZ * 16 + 1;

					if (j2 < 0) j2 = 0;

					if (k > 16) k = 16;

					if (k2 < 1) k2 = 1;

					if (l > 120) l = 120;

					if (l2 < 0) l2 = 0;

					if (i1 > 16) i1 = 16;

					boolean flag2 = false;

					for (int j1 = j2; !flag2 && j1 < k; ++j1) {
						for (int k1 = l2; !flag2 && k1 < i1; ++k1) {
							for (int l1 = l + 1; !flag2 && l1 >= k2 - 1; --l1) {
								if (l1 >= 0 && l1 < 128) {
									IBlockState iblockstate = primer.getBlockState(j1, l1, k1);

									if (iblockstate.getBlock() == Blocks.FLOWING_LAVA
											|| iblockstate.getBlock() == Blocks.LAVA) {
										flag2 = true;
									}

									if (l1 != k2 - 1 && j1 != j2 && j1 != k - 1 && k1 != l2 && k1 != i1 - 1) {
										l1 = k2;
									}
								}
							}
						}
					}

					if (!flag2) {
						for (int i3 = j2; i3 < k; ++i3) {
							double d10 = ((double) (i3 + chunkX * 16) + 0.5D - originX) / horizRadius;

							for (int j3 = l2; j3 < i1; ++j3) {
								double d8 = ((double) (j3 + chunkZ * 16) + 0.5D - originZ) / horizRadius;

								for (int i2 = l; i2 > k2; --i2) {
									double d9 = ((double) (i2 - 1) + 0.5D - originY) / vertRadius;

									if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
										IBlockState iblockstate1 = primer.getBlockState(i3, i2, j3);

										if (iblockstate1.getBlock() == Blocks.NETHERRACK
												|| iblockstate1.getBlock() == Blocks.DIRT
												|| iblockstate1.getBlock() == Blocks.GRASS) {
											primer.setBlockState(i3, i2, j3, AIR);
										}
									}
								}
							}
						}

						if (flag1) {
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
	protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int originalX, int originalZ,
			ChunkPrimer chunkPrimerIn) {
		int caveCount = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1); // between 0 & 9

		if (this.rand.nextInt(5) != 0) { // 80%
			caveCount = 0;
		}

		for (int j = 0; j < caveCount; ++j) {
			double d0 = (double) (chunkX * 16 + this.rand.nextInt(16));
			double d1 = (double) this.rand.nextInt(128);
			double d2 = (double) (chunkZ * 16 + this.rand.nextInt(16));
			int k = 1;

			if (this.rand.nextInt(4) == 0) { // 25% room
				this.addRoom(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, d0, d1, d2);
				k += this.rand.nextInt(4);
			}

			for (int l = 0; l < k; ++l) {
				float yaw = this.rand.nextFloat() * ((float) Math.PI * 2F);
				float pitch = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
				float radius = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
				this.addTunnel(this.rand.nextLong(), originalX, originalZ, chunkPrimerIn, d0, d1, d2, radius * 2.0F, yaw, pitch,
						0, 0, 0.5D);
			}
		}
	}
}