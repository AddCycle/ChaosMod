package net.chaos.chaosmod.commands.market;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.commands.AbstractPermissionFreeCommand;
import net.chaos.chaosmod.common.capabilities.IMoney;
import net.chaos.chaosmod.common.capabilities.MoneyProvider;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class MoneyCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "money";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/money [player] [set|add] [amount]";
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
	    List<String> completions = new ArrayList<>();

	    // args.length == 1 → player names
	    if (args.length == 1) {
	        String prefix = args[0];
	        for (EntityPlayerMP player : server.getPlayerList().getPlayers()) {
	            if (player.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
	                completions.add(player.getName());
	            }
	        }
	    }

	    // args.length == 2 → "set" or "add" (admin only)
	    else if (args.length == 2 && sender.canUseCommand(2, getName())) {
	        String prefix = args[1].toLowerCase();
	        if ("set".startsWith(prefix)) completions.add("set");
	        if ("add".startsWith(prefix)) completions.add("add");
	    }

	    return completions;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) {
			if (sender instanceof EntityPlayerMP) {
				EntityPlayerMP player = (EntityPlayerMP) sender;
				IMoney money = player.getCapability(MoneyProvider.MONEY_CAPABILITY, null);
				if (money != null) {
					player.sendMessage(new TextComponentString("You have " + money.get() + " money."));
				}
			} else {
				throw new WrongUsageException("Console must use /money <player>");
			}
			return;
		}

		// Case 2: /money <player> set/add <amount> (admin only)
		if (args.length == 3) {
			// Permission check
			if (!sender.canUseCommand(2, getName())) {
				throw new CommandException("You do not have permission to modify money.");
			}

			EntityPlayerMP target = getPlayer(server, sender, args[0]);
			IMoney money = target.getCapability(MoneyProvider.MONEY_CAPABILITY, null);
			if (money == null) throw new CommandException("Target has no money capability.");

			String action = args[1];
			int amount = parseInt(args[2], Integer.MIN_VALUE, Integer.MAX_VALUE);

			switch (action.toLowerCase()) {
			case "set":
				money.set(amount);
				notifyCommandListener(sender, this, "Set %s's money to %s", target.getName(), amount);
				break;
			case "add":
				money.add(amount);
				notifyCommandListener(sender, this, "Added %s to %s's money (new balance: %s)",
						amount, target.getName(), money.get());
				break;
			default:
				throw new WrongUsageException(getUsage(sender));
			}
			return;
		}

		// Invalid usage
		throw new WrongUsageException(getUsage(sender));
	}
}