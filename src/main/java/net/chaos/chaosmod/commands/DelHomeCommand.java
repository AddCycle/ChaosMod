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

public class DelHomeCommand extends CommandBase {

	@Override
	public String getName() {
		return "delhome";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/delhome <home_name>" + TextFormatting.RED + "" + TextFormatting.BOLD + " (!! it removes the home definitely !!)";
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
		if (args.length > 0) {
			String key = Reference.PREFIX + args[0] + "_homepos";
			String prev_home_key = Reference.PREFIX + "prevhomepos";
			boolean hasPosition = player.getEntityData().getIntArray(key).length != 0;
			boolean hasPrevKey = player.getEntityData().getIntArray(prev_home_key).length != 0;
			if (hasPosition) {
				int[] tag1 = player.getEntityData().getIntArray(key);
				int[] tag2 = player.getEntityData().getIntArray(prev_home_key);

				if (hasPrevKey && tag1[0] == tag2[0] && tag1[1] == tag2[1] && tag1[2] == tag2[2]) {
					Main.getLogger().info("tag1 : {}, {}, {}", tag1[0], tag1[1], tag1[2]);
					Main.getLogger().info("tag2 : {}, {}, {}", tag2[0], tag2[1], tag2[2]);

					player.getEntityData().removeTag(prev_home_key);
				}
				player.getEntityData().removeTag(key);

				String homesNumberKey = Reference.PREFIX + "homesNumber";
				if (player.getEntityData().hasKey(homesNumberKey)) {
					Main.getLogger().debug("HOME NUMBERS : {}", player.getEntityData().getInteger(homesNumberKey));
					player.getEntityData().setInteger(homesNumberKey, player.getEntityData().getInteger(homesNumberKey) - 1);
				}
				player.sendMessage(new TextComponentString("Deleted home : " + args[0] + " temp stored pos"));
			} else {
				player.sendMessage(new TextComponentString("It appears this home doesn't exists yet"));
			}
		} else {
			player.sendMessage(new TextComponentString("You have to specify a home to delete !"));
			player.sendMessage(new TextComponentString("Consider doing "+ TextFormatting.GOLD + "/delhome <name>"));
			return;
		}
	}

}
