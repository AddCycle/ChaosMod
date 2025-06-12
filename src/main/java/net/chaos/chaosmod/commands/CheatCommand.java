package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;

public class CheatCommand extends CommandBase {

	@Override
	public String getName() {
		return "ultimate_debugger";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/ultimate_debugger";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		player.setGameType(GameType.CREATIVE);
		player.capabilities.allowFlying = true;
		player.capabilities.disableDamage = true;
		player.capabilities.setFlySpeed(2f);
		player.capabilities.setPlayerWalkSpeed(1.4f);
		sender.sendMessage(new TextComponentString("T'es un ouf").setStyle(new Style().setColor(TextFormatting.BLUE).setBold(true)));
	}

}
