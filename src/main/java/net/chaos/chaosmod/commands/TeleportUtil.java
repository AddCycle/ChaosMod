package net.chaos.chaosmod.commands;

import net.chaos.chaosmod.commands.DimensionWarpCommand.CommandTeleporter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
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
		// int oldDimension = player.getEntityWorld().provider.getDimension();
		// EntityPlayerMP entityPlayerMP = ((EntityPlayerMP) player);
		MinecraftServer server = player.getEntityWorld().getMinecraftServer();
		// WorldServer worldServer = server.getWorld(dimension);
		// worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(entityPlayerMP, dimension, new TeleportUtil(worldServer, x, y, z));
		player.changeDimension(dimension, new CommandTeleporter(new BlockPos(x, y, z)));
		player.setPositionAndUpdate(x, y, z);
	}

}
