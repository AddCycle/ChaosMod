package net.chaos.chaosmod.commands.jobs;

import net.chaos.chaosmod.network.packets.JobsCommandMessage;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.command.CommandTreeBase;
import net.minecraftforge.server.command.CommandTreeHelp;

// TODO : needs refactor
// FIXME : the second part is broken, nothing is working because of the huge fix of jobs
public class JobsCommand extends CommandTreeBase {

	public JobsCommand() {
		super.addSubcommand(new JobsListCommand());
		super.addSubcommand(new JobsResetCommand());
        super.addSubcommand(new CommandTreeHelp(this));
	}

	@Override
	public String getName() { return "jobs"; }

	@Override
	public String getUsage(ICommandSender sender) {
		return "Use /jobs <subcommand>";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);

		if (args.length == 0) {
			PacketManager.network.sendTo(new JobsCommandMessage(), player);
			player.sendStatusMessage(new TextComponentString("job gui opened"), true);
		}
		
		super.execute(server, sender, args);
	}
}
