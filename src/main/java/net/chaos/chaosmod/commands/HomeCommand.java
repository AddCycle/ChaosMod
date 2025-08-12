package net.chaos.chaosmod.commands;

import net.chaos.chaosmod.Main;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class HomeCommand extends CommandBase {

	@Override
	public String getName() {
		return "home";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/home <home_name> (if no args, teleports to the early one)";
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
		if (args.length > 0) {
			String key = Reference.PREFIX + args[0] + "_homepos";
			boolean hasPosition = player.getEntityData().getIntArray(key).length != 0;
			if (hasPosition) {
				int[] playerPos = player.getEntityData().getIntArray(key);
				// TeleportUtil.teleport(player, player.dimension, playerPos[0], playerPos[1], playerPos[2]);
				player.setPositionAndUpdate(playerPos[0], playerPos[1], playerPos[2]);
			} else {
				player.sendMessage(new TextComponentString("It appears you do not have a home yet !"));
				player.sendMessage(new TextComponentString("Consider doing "+ TextFormatting.GOLD + "/sethome <name>"));
			}

			if (player.getEntityData().hasKey(homesNumberKey)) {
				Main.getLogger().debug("HOME NUMBERS : {}", player.getEntityData().getInteger(homesNumberKey));
			}
		} else {
			// player previous home tp
			String key = Reference.PREFIX + "prevhomepos";
			boolean hasPosition = player.getEntityData().getIntArray(key).length != 0;
			if (hasPosition) {
				int[] playerPos = player.getEntityData().getIntArray(key);
				player.getEntityData().setIntArray(key, playerPos);
				player.setPositionAndUpdate(playerPos[0], playerPos[1], playerPos[2]);
			} else {
				player.sendMessage(new TextComponentString("It appears you do not have a home yet !"));
				player.sendMessage(new TextComponentString("Consider doing "+ TextFormatting.GOLD + "/sethome <name>"));
			}
			if (player.getEntityData().hasKey(homesNumberKey)) {
				Main.getLogger().debug("HOME NUMBERS : {}", player.getEntityData().getInteger(homesNumberKey));
			}
			return;
		}

	}

}
