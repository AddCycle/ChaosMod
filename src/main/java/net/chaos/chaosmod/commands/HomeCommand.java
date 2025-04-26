package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import util.Reference;

public class HomeCommand extends CommandBase {

	@Override
	public String getName() {
		return "home";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/home";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		boolean hasPosition = player.getEntityData().getIntArray(Reference.MODID + "_homepos").length != 0;
		if (hasPosition) {
			int[] playerPos = player.getEntityData().getIntArray(Reference.MODID + "_homepos");
			player.setPositionAndUpdate(playerPos[0], playerPos[1], playerPos[2]);
		} else {
			player.sendMessage(new TextComponentString("It appears you do not have a home yet !"));
			player.sendMessage(new TextComponentString("Consider doing /sethome"));
		}
	}

}
