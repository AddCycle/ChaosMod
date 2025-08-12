package net.chaos.chaosmod.commands;

import java.util.Set;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class HomeListCommand extends CommandBase {

	@Override
	public String getName() {
		return "homelist";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/homelist (shows all your current homes)";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 0; // every player is allowed to use it
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true; // safety check everyone should be able to use it
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		String homesNumberKey = Reference.PREFIX + "homesNumber";
		Set<String> homeSet = player.getEntityData().getKeySet();
		
		// Lists homes
		homeSet.forEach(key -> {
			if (key.startsWith(Reference.PREFIX) && (key.endsWith("_homepos") || key.endsWith("prevhomepos"))) {
				int[] home = player.getEntityData().getIntArray(key);
				String playerPos = String.format(TextFormatting.RED + "" + TextFormatting.BOLD + "%s" + TextFormatting.RESET + " =>" + TextFormatting.GOLD + " %d %d %d", key, home[0], home[1], home[2]);
				player.sendMessage(new TextComponentString(playerPos));
			}
		});
		
		// Displays count
		homeSet.forEach(key -> {
			if (key.equalsIgnoreCase(homesNumberKey)) {
				int homesNumber = player.getEntityData().getInteger(key);
				player.sendMessage(new TextComponentString(String.format("You have currently" + TextFormatting.BOLD + " %d" + TextFormatting.RESET + " home(s).", homesNumber)));
			}
		});
		if (homeSet.isEmpty()) {
			player.sendMessage(new TextComponentString("It appears you do not have a home yet !"));
			player.sendMessage(new TextComponentString("Consider doing "+ TextFormatting.GOLD + "/sethome <name>"));
		}
	}
}
