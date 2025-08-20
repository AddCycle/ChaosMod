package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public abstract class AbstractPermissionFreeCommand extends CommandBase {

	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}
}
