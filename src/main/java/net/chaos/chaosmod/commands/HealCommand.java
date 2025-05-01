package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class HealCommand extends CommandBase {

	@Override
	public String getName() {
		return "heal";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/heal";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		player.setHealth(player.getMaxHealth());
		notifyCommandListener(sender, this, "command.heal.successful", new Object[] {player.getDisplayName()});
	}

}
