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
		World entityWorld = player.getEntityWorld();
		int dimension = entityWorld.provider.getDimension();
		BlockPos pos = player.getPosition();
		int height = entityWorld.getHeight(pos.getX(), pos.getZ());
		if (pos.getY() < height) {
			TeleportUtil.teleport(player, dimension, pos.getX() + 0.5, height, pos.getZ() + 0.5);
		}
	}
}
