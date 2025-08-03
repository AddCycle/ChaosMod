package net.chaos.chaosmod.commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Loader;

public class HealCommand extends CommandBase {

	@Override
	public String getName() {
		return "heal";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/heal [player/self] (works unless MathsMod is loaded)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = (args.length > 0) ? getPlayer(server, sender, args[0]) : getCommandSenderAsPlayer(sender);
		player.setHealth(player.getMaxHealth());
		notifyCommandListener(sender, this, "command.heal.successful", new Object[] {player.getDisplayName()});
	}
}
