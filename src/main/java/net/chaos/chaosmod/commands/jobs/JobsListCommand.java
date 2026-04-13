package net.chaos.chaosmod.commands.jobs;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.commands.AbstractPermissionFreeCommand;
import net.chaos.chaosmod.jobs.JobsManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class JobsListCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "list";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Use /jobs list";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		JobsManager.REGISTRY.keySet().forEach(id -> {
			player.sendMessage(new TextComponentString(id));
			Main.getLogger().info(id);
		});
	}
}