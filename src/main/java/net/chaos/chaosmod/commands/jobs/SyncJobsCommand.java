package net.chaos.chaosmod.commands.jobs;

import java.util.List;

import net.chaos.chaosmod.network.PacketManager;
import net.chaos.chaosmod.network.PacketSyncJobs;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import scala.actors.threadpool.Arrays;

public class SyncJobsCommand extends CommandBase {

	@Override
	public String getName() {
		return "sync_jobs";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/sync_jobs (sync client & server sending auth packet)";
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList(new String[] {"sync_jobs", "reload_jobs"});
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 0;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		PacketManager.network.sendTo(new PacketSyncJobs(), (EntityPlayerMP) player);
		player.sendMessage(new TextComponentString("Packet sent reloaded jobs"));
	}

}
