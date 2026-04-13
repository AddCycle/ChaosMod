package net.chaos.chaosmod.commands.jobs;

import java.util.Collections;
import java.util.List;

import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.JobProgress;
import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketSyncPlayerJobs;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class JobsResetCommand extends CommandBase {

	@Override
	public String getName() { return "reset"; }

	@Override
	public String getUsage(ICommandSender sender) {
		return "Use /jobs reset <player> <job_id | all> (doesn't work for shared tasks)";
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
		if (args.length == 1) {
			List<String> list = getListOfStringsMatchingLastWord(args, JobsManager.REGISTRY.keySet());
			list.add("all");
			return list;
		}
		
		if (args.length == 2)
			return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		
		return Collections.emptyList();
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length != 2) {
			throw new WrongUsageException(getUsage(sender));
		}

		EntityPlayerMP player = getPlayer(server, sender, args[0]);
		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs == null) {
			player.sendMessage(new TextComponentString("Jobs Capability is null for the player, cannot reset."));
			return;
		}

		if (args[1].equalsIgnoreCase("all")) {
			JobsManager.REGISTRY.keySet().forEach(id -> jobs.setProgress(id, new JobProgress(0, 0)));
			player.sendMessage(new TextComponentString("Jobs successfully reset for " + args[0]));
		} else {
			jobs.setProgress(args[0], new JobProgress(0, 0));
			player.sendMessage(new TextComponentString("Job: " + args[1] + " is successfully reset for " + args[0]));
		}

		syncJobCapabilities(player);
	}

	private static void syncJobCapabilities(EntityPlayerMP player) {
		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		PacketManager.network.sendTo(new PacketSyncPlayerJobs(jobs), player);
	}
}