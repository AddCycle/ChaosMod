package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class FireCommand extends CommandBase {

	@Override
	public String getName() {
		return "fire";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/fire <duration>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		int duration = 0;
		try {
			duration = parseInt(args[0]);
		} catch (NumberInvalidException e) {}
		if (!player.isBurning()) player.setFire(duration == 0 ? 2 : duration);
	}

}
