package net.chaos.chaosmod.commands.debug;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class OverlayCommand extends CommandBase {

	@Override
	public String getName() {
		return "overlay";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/overlay [0..2]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		
	}

}
