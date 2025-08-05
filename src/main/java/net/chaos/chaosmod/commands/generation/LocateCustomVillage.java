package net.chaos.chaosmod.commands.generation;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class LocateCustomVillage extends CommandBase {

	@Override
	public String getName() {
		return "locatecustomvillage";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/locatecustomvillage";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
	}

}
