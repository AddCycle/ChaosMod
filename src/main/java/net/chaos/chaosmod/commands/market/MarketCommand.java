package net.chaos.chaosmod.commands.market;

import net.chaos.chaosmod.commands.AbstractPermissionFreeCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

public class MarketCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "market";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/market";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		player.sendMessage(new TextComponentTranslation("COMING SOON!"));
	}
}