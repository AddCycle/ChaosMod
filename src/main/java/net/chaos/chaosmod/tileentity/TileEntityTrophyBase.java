package net.chaos.chaosmod.tileentity;

import net.chaos.chaosmod.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityTrophyBase extends TileEntity implements ITickable {

	@Override
	public void update() {
		int r = 4;
		int cnt = 30;
		spawnParticleCircle(world, pos, r, cnt, EnumParticleTypes.REDSTONE);
		applyEffectBasedOnRange(r, new PotionEffect(ModPotions.POTION_VIKING, 20 * 1, 0));
	}

	private void applyEffectBasedOnRange(int range, PotionEffect effectIn) {
		if (world.isRemote) return;
		for (EntityPlayer pl : this.getWorld().playerEntities) {
			pl.addPotionEffect(effectIn);
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
