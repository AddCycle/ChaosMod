package net.chaos.chaosmod.tileentity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityBossAltar extends TileEntity implements ITickable {
	private static final Logger LOGGER = LogManager.getLogger("ChaosMod");
	public boolean readyToSpawnBoss = false;
	public int animationTicks = 0;
	public boolean isAnimating = false;
	public float duration = 0;
	public float r = 0.001f, g = 0.0f, b = 1.0f;
	
	public void triggerAnimation(int duration) {
		this.animationTicks = duration;
	    isAnimating = true;
	    this.duration = duration;

	    if (world != null && !world.isRemote) {
	        world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
	    }
	}

	@Override
	public void update() {
		if (world.isRemote) {
	        // Client tick: progress animation locally
	        if (isAnimating) {
	            animationTicks--;
	            if (animationTicks <= 0) {
	                isAnimating = false;
	            }
	            if (animationTicks > 0) {
	            	spawnParticles(r, g, b);
	            }
	        }
	    } else {
	        // Server tick: countdown separately
	        if (isAnimating) {
	            animationTicks--;
	            if (animationTicks <= 0) {
	                isAnimating = false;
	                readyToSpawnBoss = true;
	                LOGGER.info("Updating server side for TileEntity : {}", pos);
	            }
	        }

	        if (readyToSpawnBoss) {
	            readyToSpawnBoss = false; // Reset flag
	            animationFinished();
	        }
	    }
	}
	
	public void animationFinished() {
		spawnBoss();
	}
	
	public void spawnBoss() {
		World world = this.world;
		BlockPos pos = this.getPos();

		if (!world.isRemote) {
			EntityBlaze boss = new EntityBlaze(world);
			boss.setPosition(pos.getX() + 0.5, pos.up().getY(), pos.getZ() + 0.5);
			boss.forceSpawn = true;
			world.spawnEntity(boss);
		}
		System.out.println("Boss spawned");
	}
	
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
	
	// suggested changes to trigger the animation server side
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
	    return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
	    handleUpdateTag(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
	    return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
	    this.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
	    super.writeToNBT(compound);
	    compound.setBoolean("IsAnimating", this.isAnimating);
	    compound.setInteger("AnimationTicks", this.animationTicks);
	    compound.setFloat("Duration", this.duration);
	    return compound;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
	    super.readFromNBT(compound);
	    this.isAnimating = compound.getBoolean("IsAnimating");
	    this.animationTicks = compound.getInteger("AnimationTicks");
	    this.duration = compound.getFloat("Duration");
	}

}
