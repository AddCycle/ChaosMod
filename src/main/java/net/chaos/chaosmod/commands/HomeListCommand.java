package net.chaos.chaosmod.commands;

import java.util.Set;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class HomeListCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "homelist";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/homelist";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		String homeCountKey = Reference.PREFIX + "homesNumber";
		Set<String> homeSet = player.getEntityData().getKeySet();
//		homeSet.stream().filter(key -> key.startsWith(Reference.PREFIX) && (key.endsWith("_homepos") || key.endsWith("prevhomepos")));

		if (homeSet.isEmpty()) {
			player.sendMessage(new TextComponentString("It appears you do not have a home yet !"));
			player.sendMessage(new TextComponentString("Consider doing "+ TextFormatting.GOLD + "/sethome <name>"));
			return;
		}
		
		// Lists homes
		homeSet.forEach(key -> {
			if (key.startsWith(Reference.PREFIX) && ((key.endsWith("_homepos") || key.endsWith("prevhomepos")))) {
				int[] home = player.getEntityData().getIntArray(key);
				String playerPos = String.format("%s%s%s%s =>%s %d %d %d", TextFormatting.RED, TextFormatting.BOLD, key, TextFormatting.RESET, TextFormatting.GOLD, home[0], home[1], home[2]);
				player.sendMessage(new TextComponentString(playerPos));
			}
		});
		
		// Displays count
		homeSet.forEach(key -> {
			if (key.equalsIgnoreCase(homeCountKey)) {
				int homesNumber = player.getEntityData().getInteger(key);
				player.sendMessage(new TextComponentString(String.format("You have currently" + TextFormatting.BOLD + " %d" + TextFormatting.RESET + " home(s).", homesNumber)));
			}
		});
	}
}
