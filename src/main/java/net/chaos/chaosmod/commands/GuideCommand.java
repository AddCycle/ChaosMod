package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class GuideCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "guide";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/guide (gives quest/guide-book)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);

		player.sendMessage(new TextComponentString("work in progress..."));
	}
}