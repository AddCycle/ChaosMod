package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import util.Reference;

public class SetHomeCommand extends CommandBase {

	@Override
	public String getName() {
		return "sethome";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/sethome";
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
		player.getEntityData().setIntArray(Reference.MODID + "_homepos", new int[] { playerPos.getX(), playerPos.getY(), playerPos.getZ() });
		player.sendMessage(new TextComponentString("Position set at : " + playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ()));
	}

}
