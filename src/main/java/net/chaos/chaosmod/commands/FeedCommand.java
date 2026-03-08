package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.MathHelper;

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
		if (args.length > 0) {
			int amount;
			try {
				amount = parseInt(args[0]);
			} catch (NumberInvalidException e) {
				amount = 20;
			}
			player.getFoodStats().setFoodLevel(MathHelper.clamp(amount, 0, 20));
			return;
		}

		if (foodLevel != 20)
			player.getFoodStats().setFoodLevel(20);
	}

}
