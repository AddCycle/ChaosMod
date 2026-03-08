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
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			@Nullable BlockPos targetPos) {
		List<String> completions = new ArrayList<>();

		// player names
		if (args.length == 1) {
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		}

		else if (args.length == 2 && sender.canUseCommand(2, getName())) {
			setCompletion(args, completions);
		}

		return completions;
	}

	private void setCompletion(String[] args, List<String> completions) {
		String prefix = args[1].toLowerCase();
		for (MoneyAction action : MoneyAction.values()) {
			String name = action.name().toLowerCase();

			if (name.startsWith(prefix)) {
				completions.add(name);
			}
		}
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) {
			showOwnMoney(sender);
			return;
		}

		if (args.length == 3) {
			handleAdminMoney(server, sender, args);
			return;
		}

		throw new WrongUsageException(getUsage(sender));
	}

	private void showOwnMoney(ICommandSender sender) throws CommandException {
		if (!(sender instanceof EntityPlayerMP)) {
			throw new WrongUsageException("Console must use /money <player>");
		}

		EntityPlayerMP player = (EntityPlayerMP) sender;
		IMoney money = getMoney(player);

		player.sendMessage(new TextComponentString(String.format("You have %d$", money.get())));
	}

	/**
	 * set/add money to the specified player
	 * 
	 * @param server
	 * @param sender
	 * @param args
	 * @throws CommandException
	 */
	private void handleAdminMoney(MinecraftServer server, ICommandSender sender, String[] args)
			throws CommandException {

		checkPermission(sender);

		EntityPlayerMP target = getPlayer(server, sender, args[0]);
		IMoney money = getMoney(target);

		MoneyAction action = getAction(sender, args[1]);
		int amount = parseInt(args[2], Integer.MIN_VALUE, Integer.MAX_VALUE);

		applyAction(sender, target, money, action, amount);
	}

	private IMoney getMoney(EntityPlayerMP player) throws CommandException {
		IMoney money = player.getCapability(MoneyProvider.MONEY_CAPABILITY, null);
		if (money == null) {
			throw new CommandException("Target has no money capability.");
		}
		return money;
	}

	private void applyAction(ICommandSender sender, EntityPlayerMP target, IMoney money, MoneyAction action, int amount)
			throws WrongUsageException {
		switch (action) {
			case SET:
				money.set(amount);
				notifyCommandListener(sender, this, "Set %s's money to %s", target.getName(), amount);
				break;

			case ADD:
				money.add(amount);
				notifyCommandListener(sender, this, "Added %s to %s's money (new balance: %s)", amount,
						target.getName(), money.get());
				break;

			default:
				throw new WrongUsageException(getUsage(sender));
		}

	}

	private void checkPermission(ICommandSender sender) throws CommandException {
		if (!sender.canUseCommand(2, getName())) {
			throw new CommandException("You don't have permission to modify money.");
		}
	}

	private enum MoneyAction {
		SET, ADD;
	}

	private MoneyAction getAction(ICommandSender sender, String action) throws WrongUsageException {
		MoneyAction result;
		try {
			result = MoneyAction.valueOf(action.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new WrongUsageException(getUsage(sender));
		}

		return result;
	}
}