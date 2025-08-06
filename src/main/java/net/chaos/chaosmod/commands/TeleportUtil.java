package net.chaos.chaosmod.commands;

import net.chaos.chaosmod.commands.DimensionWarpCommand.CommandTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleportUtil extends Teleporter {
	private double x, y, z;
	private WorldServer world;

	public TeleportUtil(WorldServer worldIn, double x, double y, double z) {
		super(worldIn);
		this.world = worldIn;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void placeInPortal(Entity entity, float rotationYaw) {
		this.world.getBlockState(new BlockPos((int) this.x, (int) this.y, (int) this.z));
		entity.setPosition(this.x, this.y, this.z);
		entity.motionX = 0.f;
		entity.motionY = 0.f;
		entity.motionZ = 0.f;
	}
	
	public static void teleport(EntityPlayer player, int dimension, double x, double y, double z) {
		player.changeDimension(dimension, new CommandTeleporter(new BlockPos(x, y, z)));
	}

}
