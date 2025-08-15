package net.chaos.chaosmod.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.jobs.JobProgress;
import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.PlayerJobsManager;
import net.chaos.chaosmod.network.JobsCommandMessage;
import net.chaos.chaosmod.network.PacketManager;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class JobsCommand extends CommandBase implements ICommand {
	public List<String> aliases = new ArrayList<String>();
	public List<String> completion = new ArrayList<String>();

	@Override
	public String getName() {
		return "jobs";
	}
	
	@Override
    public List<String> getAliases()
    {
		List<String> temp = Arrays.asList("job", "jbos", "bojs", "sojb");
        aliases.addAll(temp);
        return aliases;
    }

	@Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
    {
		if (completion.isEmpty()) {
			for (Item i : ModItems.ITEMS) {
				this.completion.add(i.getRegistryName().toString());
			}
		}
		return this.completion;
    }

	@Override
	public String getUsage(ICommandSender sender) {
		return "/jobs or /jobs list or /jobs job_id <xp> [amount]";
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
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		World world = player.world;
		if (args.length > 0) {
			switch (args.length) {
			case 1: // list jobs
				if (args[0].equalsIgnoreCase("list")) {
					if (!world.isRemote) {
						player.sendMessage(new TextComponentString("Jobs : " + JobsManager.REGISTRY.size()));
						JobsManager.REGISTRY.forEach((jobId, job) -> {
							player.sendMessage(new TextComponentString(job.id + ": " + job.description));
						});
					}
				}
				break;
			case 3:
				if (args[1].equalsIgnoreCase("xp")) {
					Main.getLogger().info("Jobs : Command sent increase exp");
					JobProgress.addExp(player, args[0], parseInt(args[2]));
					player.sendMessage(new TextComponentString("success"));
				}
				break;
			default:
				break;
			}
		} else {
			if (!world.isRemote) {
				PacketManager.network.sendTo(new JobsCommandMessage(), player);
				player.sendMessage(new TextComponentString("[Server] : jobs gui opened"));
			}
			player.sendMessage(new TextComponentString("[Client] : jobs gui opened"));
		}
	}
}
