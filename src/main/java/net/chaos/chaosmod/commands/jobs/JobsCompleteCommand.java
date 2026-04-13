package net.chaos.chaosmod.commands.jobs;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

// TODO : implement
public class JobsCompleteCommand extends CommandBase {

	@Override
	public String getName() {
		return "complete";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Use /jobs complete <job_id> <task_id | all>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		player.sendStatusMessage(new TextComponentString("in development..."), true);

	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		return Collections.emptyList();
	}
}