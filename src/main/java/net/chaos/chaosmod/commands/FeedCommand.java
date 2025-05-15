package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

public class FeedCommand extends CommandBase {

	@Override
	public String getName() {
		return "feed";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/feed [amount]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		int foodLevel = player.getFoodStats().getFoodLevel();
		if (args.length == 0) {
			if (foodLevel != 20) player.getFoodStats().setFoodLevel(20);
		} else {
			int amount = parseInt(args[0]);
			if (amount <= 20 && amount > 0)
				player.getFoodStats().setFoodLevel(amount);
			else
				player.sendCommandFeedback();
		}
	}

}
