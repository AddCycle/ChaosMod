package net.chaos.chaosmod.commands.debug;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class SpeedCommand extends CommandBase {

	@Override
	public String getName() {
		return "speed";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Use /speed [on/off]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		if (args.length < 1) {
			throw new WrongUsageException(getUsage(sender));
		}

		if (args[0].equalsIgnoreCase("on")) {
			int multiplier = 5;
			player.capabilities.setFlySpeed(0.05f * multiplier);
			player.capabilities.setPlayerWalkSpeed(0.1f * multiplier);
			return;
		} else if (args[0].equalsIgnoreCase("off")) {
			player.capabilities.setFlySpeed(0.05f);
			player.capabilities.setPlayerWalkSpeed(0.1f);
			return;
		}
		
		throw new WrongUsageException(getUsage(sender));
	}
}