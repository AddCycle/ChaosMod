package net.chaos.chaosmod.client.particles;

import net.minecraft.client.particle.ParticleSweepAttack;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.World;

public class ColoredSweepParticle extends ParticleSweepAttack {
	public ColoredSweepParticle(TextureManager textureManagerIn, World worldIn, double x, double y, double z,
			double dx, double dy, double dz, int colorHex) {
		super(textureManagerIn, worldIn, x, y, z, dx, dy, dz);
		int color = colorHex;
		// System.out.println(String.format("Computed particle color rgb: #%06x", color));

		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = (color) & 0xFF;

		System.out.println("red : " + red);
		System.out.println("green : " + green);
		System.out.println("blue : " + blue);

		// Normalize to [0.0, 1.0] float values
		this.particleRed = red / 255.0f;
		this.particleGreen = green / 255.0f;
		this.particleBlue = blue / 255.0f;
		this.particleAge = 100; // 5 seconds
	}
	
	@Override
	public int getFXLayer() {
		return super.getFXLayer();
	}

}
