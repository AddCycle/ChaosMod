package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class SetHomeCommand extends AbstractPermissionFreeCommand {
	private static final int MAX_HOME_COUNT = 3;

	@Override
	public String getName() {
		return "sethome";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/sethome <home_name>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);

		if (args.length < 1) {
			player.sendMessage(new TextComponentString(getUsage(sender)));
			return;
		}

		String homeName = args[0];
		String currentHomeKey = Reference.PREFIX + homeName + "_homepos";
		String previousHomeKey = Reference.PREFIX + "prevhomepos";
		String homeCountKey = Reference.PREFIX + "homesNumber";

		if (player.getEntityData().hasKey(homeCountKey)) {
			int homeNumbers = player.getEntityData().getInteger(homeCountKey);
			if (homeNumbers >= MAX_HOME_COUNT) {
				player.sendMessage(new TextComponentString(
						"You have already " + MAX_HOME_COUNT + " homes consider deleting one using : /delhome <name>"));
				return;
			}
			player.getEntityData().setInteger(homeCountKey, homeNumbers + 1);
		} else {
			player.getEntityData().setInteger(homeCountKey, 1);
		}

		BlockPos playerPos = player.getPosition();

		player.getEntityData().setIntArray(currentHomeKey,
				new int[] { playerPos.getX(), playerPos.getY(), playerPos.getZ() });
		player.getEntityData().setIntArray(previousHomeKey,
				new int[] { playerPos.getX(), playerPos.getY(), playerPos.getZ() });
		player.sendMessage(new TextComponentString(
				"Home " + TextFormatting.RED + "" + TextFormatting.BOLD + homeName + TextFormatting.RESET
						+ " set at pos : " + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ()));
	}
}
