package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayerMP;

public class TopCommand extends CommandBase {

	@Override
	public String getName() {
		return "top";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/top";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		World world = player.getEntityWorld();

		int dimension = world.provider.getDimension();
		BlockPos pos = player.getPosition();
		int height = world.getHeight(pos.getX(), pos.getZ());

		if (pos.getY() < height) {
			TeleportUtil.teleportCenter(player, dimension, pos.getX(), height, pos.getZ());
		}
	}
}
