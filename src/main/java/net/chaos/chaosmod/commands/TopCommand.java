package net.chaos.chaosmod.commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;

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
		while (player.getServerWorld().getBlockState(player.getPosition().up()).getBlock() != Block.getBlockById(0)) {
			player.setPosition(player.getPosition().getX(), player.getPosition().getY() + 2, player.getPosition().getZ());
		}
	}
}
