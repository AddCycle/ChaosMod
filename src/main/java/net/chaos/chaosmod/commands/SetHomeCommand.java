package net.chaos.chaosmod.commands;

import net.chaos.chaosmod.Main;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import util.Reference;

public class SetHomeCommand extends CommandBase {

	@Override
	public String getName() {
		return "sethome";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/sethome <home_name> (mendatory)";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0; // every player is allowed to use it also
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true; // safety check everyone should be able to use it
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		BlockPos playerPos = player.getPosition();
		if (args.length > 0) {
			String home_name = args[0];
			String current_home_key = Reference.PREFIX + home_name + "_homepos";
			String previous_home_key = Reference.PREFIX + "prevhomepos";
			String homesNumberKey = Reference.PREFIX + "homesNumber";
			if (player.getEntityData().hasKey(homesNumberKey)) {
				int homeNumbers = player.getEntityData().getInteger(homesNumberKey);
				if (homeNumbers >= 3) {
					player.sendMessage(new TextComponentString("You have already 3 homes consider deleting one using : /delhome <name>"));
					return;
				}
				player.getEntityData().setInteger(homesNumberKey, homeNumbers + 1);
				Main.getLogger().info("HOME NUMBERS : {}", player.getEntityData().getInteger(homesNumberKey));
			} else {
				player.getEntityData().setInteger(homesNumberKey, 1);
			}
			player.getEntityData().setIntArray(current_home_key, new int[] { playerPos.getX(), playerPos.getY(), playerPos.getZ() });
			player.getEntityData().setIntArray(previous_home_key, new int[] { playerPos.getX(), playerPos.getY(), playerPos.getZ() });
			player.sendMessage(new TextComponentString("Home " + TextFormatting.RED + "" + TextFormatting.BOLD + home_name + TextFormatting.RESET + " set at pos : " + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ()));
		} else {
			player.sendMessage(new TextComponentString("You must specify a home name : /sethome <name>"));
			return;
		}
	}

}
