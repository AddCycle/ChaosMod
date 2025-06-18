package net.chaos.chaosmod.client.particles;

import net.minecraft.client.particle.ParticleEndRod;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CoinParticles extends ParticleEndRod {

	public CoinParticles(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn,
			double ySpeedIn, double zSpeedIn) {
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
	}

	/*@Override
	public int getFXLayer() {
		return 3;
	}*/

}
