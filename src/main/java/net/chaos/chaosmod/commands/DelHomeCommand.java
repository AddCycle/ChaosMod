package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class DelHomeCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "delhome";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/delhome <home_name>" + TextFormatting.RED + "" + TextFormatting.BOLD
				+ " (!! it removes the home definitely !!)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);

		if (args.length < 1) {
			player.sendMessage(new TextComponentString("You have to specify a home to delete !"));
			player.sendMessage(new TextComponentString("Consider doing " + TextFormatting.GOLD + "/delhome <name>"));
			return;
		}

		String homeKey = Reference.PREFIX + args[0] + "_homepos";
		String previousHomeKey = Reference.PREFIX + "prevhomepos";
		boolean hasPosition = player.getEntityData().getIntArray(homeKey).length != 0;

		if (!hasPosition) {
			player.sendMessage(new TextComponentString("It appears this home doesn't exists"));
			return;
		}

		removeHome(player, homeKey, previousHomeKey);

		player.sendMessage(new TextComponentString("Deleted home : " + args[0] + " temp stored pos"));
	}

	private void removeHome(EntityPlayer player, String homeKey, String previousHomeKey) {
		int[] tag1 = player.getEntityData().getIntArray(homeKey);
		int[] tag2 = player.getEntityData().getIntArray(previousHomeKey);

		boolean hasPrevKey = player.getEntityData().getIntArray(previousHomeKey).length != 0;
		if (hasPrevKey && tag1[0] == tag2[0] && tag1[1] == tag2[1] && tag1[2] == tag2[2]) {
			player.getEntityData().removeTag(previousHomeKey);
		}
		player.getEntityData().removeTag(homeKey);

		String homeCountKey = Reference.PREFIX + "homesNumber";
		if (player.getEntityData().hasKey(homeCountKey)) {
			int previousCount = player.getEntityData().getInteger(homeCountKey);
			player.getEntityData().setInteger(homeCountKey, previousCount - 1);
		}
	}

}
