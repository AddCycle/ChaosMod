package net.chaos.chaosmod.tileentity;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBossAltar extends TileEntity implements ITickable {
	public int animationTicks = 0;
	public boolean isAnimating = false;
	public float duration = 0;
	public float r = 0.001f, g = 0.0f, b = 1.0f;
	
	public void triggerAnimation(int duration) {
		this.animationTicks = duration;
		this.isAnimating = true;
		this.duration = duration;
	}

	@Override
	public void update() {
		if (this.animationTicks > 0) {
			animationTicks--;
			if (this.animationTicks <= 0) {
				isAnimating = false;
				animationFinished();
			}
			spawnParticles(r, g, b);
		}
	}
	
	public void animationFinished() {
		spawnBoss();
	}
	
	public void spawnBoss() {
		BlockPos pos = this.getPos();
		World world = this.getWorld();

		EntityBlaze boss = new EntityBlaze(world);
		boss.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
		if (!world.isRemote) world.spawnEntity(boss);
		System.out.println("Boss spawned");
	}
	
	@SideOnly(Side.CLIENT)
	private void spawnParticles(float R, float G, float B) {
	    World world = getWorld();

	    double x = pos.getX() + 0.5;
	    double y = pos.getY() + 1.0;
	    double z = pos.getZ() + 0.5;

	    for (int i = 0; i < 3; i++) {
	        world.spawnParticle(EnumParticleTypes.REDSTONE,
	            x + (world.rand.nextGaussian() * 0.1),
	            y + (world.rand.nextGaussian() * 0.1),
	            z + (world.rand.nextGaussian() * 0.1),
	            R, G, B);
	    }
	}

}
