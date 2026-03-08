package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class HomeCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "home";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/home [home_name] (default: last home set)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);

		if (args.length > 0) {
			teleportToSpecifiedHome(player, args);
			return;
		}

		teleportToPreviousHome(player);
	}

	private void teleportToPreviousHome(EntityPlayer player) {
		String previousHomeKey = Reference.PREFIX + "prevhomepos";

		boolean hasPosition = player.getEntityData().getIntArray(previousHomeKey).length != 0;
		if (!hasPosition) {
			player.sendMessage(new TextComponentString("It appears you do not have a home yet !"));
			player.sendMessage(new TextComponentString("Consider doing " + TextFormatting.GOLD + "/sethome <name>"));
			return;
		}

		int[] playerPos = player.getEntityData().getIntArray(previousHomeKey);
		player.setPositionAndUpdate(playerPos[0], playerPos[1], playerPos[2]);
	}

	private void teleportToSpecifiedHome(EntityPlayer player, String[] args) {
		String homeName = args[0];
		String key = Reference.PREFIX + homeName + "_homepos";

		int[] playerPos = player.getEntityData().getIntArray(key);
		boolean hasPosition = playerPos.length != 0;

		if (!hasPosition) {
			player.sendMessage(new TextComponentString("It appears you do not have a home yet !"));
			player.sendMessage(new TextComponentString("Consider doing " + TextFormatting.GOLD + "/sethome <name>"));
			return;
		}

		String previousHomeKey = Reference.PREFIX + "prevhomepos";
		player.getEntityData().setIntArray(previousHomeKey, playerPos);

		player.setPositionAndUpdate(playerPos[0], playerPos[1], playerPos[2]);
	}
}
