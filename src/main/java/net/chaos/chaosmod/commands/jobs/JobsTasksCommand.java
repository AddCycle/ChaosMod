package net.chaos.chaosmod.commands.jobs;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

public class JobsTasksCommand extends CommandBase {

	@Override
	public String getName() { return "tasks"; }

	@Override
	public String getUsage(ICommandSender sender) {
		return "Use /jobs tasks <job_id | all> (lists all tasks)";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("all")) {
				listAllTasks(player);
			} else {
				listJobTasks(player, args[0]);
			}
			return;
		}

		throw new WrongUsageException(getUsage(sender));
	}

	private void listJobTasks(EntityPlayerMP player, String jobId) {
		Collection<JobTask> tasks = JobsManager.TASK_MANAGER.getTasks(jobId);
		if (tasks == null) {
			player.sendMessage(new TextComponentString("Error: no such job found = " + jobId));
			return;
		}

		tasks.forEach(task ->
		{
			player.sendMessage(new TextComponentString(task.id));
		});
	}

	private void listAllTasks(EntityPlayerMP player) {
		JobsManager.REGISTRY.keySet().forEach(id ->
		{
			JobsManager.TASK_MANAGER.getTasks(id).forEach(task ->
			{
				player.sendMessage(new TextComponentString(task.id));
			});
		});
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		List<String> list = getListOfStringsMatchingLastWord(args, JobsManager.REGISTRY.keySet());
		list.add("all");
		return args.length == 1 ? list : Collections.emptyList();
	}
}