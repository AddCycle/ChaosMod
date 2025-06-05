package net.chaos.chaosmod.tileentity;

import net.chaos.chaosmod.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityTrophyBase extends TileEntity implements ITickable {
	public double range;
	public int particles;
	public Potion potion;
	public EnumParticleTypes particleType;
	
	public TileEntityTrophyBase() {
		this(4, 30, ModPotions.POTION_VIKING, EnumParticleTypes.REDSTONE);
	}

	public TileEntityTrophyBase(double range, int particles, Potion potion, EnumParticleTypes particleTypes) {
		this.range = range;
		this.particles = particles;
		this.potion = potion;
		particleType = particleTypes;
	}

	@Override
	public void update() {
		spawnParticleCircle(world, pos, range, particles, particleType);
		applyEffectBasedOnRange(range, new PotionEffect(potion, 10, 0));
	}

	private void applyEffectBasedOnRange(double range, PotionEffect effectIn) {
		if (world.isRemote) return;
		for (EntityPlayer pl : this.world.playerEntities) {
			double dist = Math.sqrt(pl.getDistanceSqToCenter(pos));
			// System.out.println(dist);
			if (dist <= range) {
				pl.addPotionEffect(effectIn);
			}
		}
	}

	public void spawnParticleCircle(World world, BlockPos center, double radius, int particleCount, EnumParticleTypes particle) {
	    double cx = center.getX() + 0.5;
	    double cy = center.getY() + 0.5;
	    double cz = center.getZ() + 0.5;

	    for (int i = 0; i < particleCount; i++) {
	        double angle = 2 * Math.PI * i / particleCount;
	        double x = cx + radius * Math.cos(angle);
	        double z = cz + radius * Math.sin(angle);
	        world.spawnParticle(particle, x, cy, z, 0, 0, 0);
	    }
	}

}
