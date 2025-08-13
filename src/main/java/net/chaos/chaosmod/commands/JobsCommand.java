package net.chaos.chaosmod.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import net.chaos.chaosmod.init.ModItems;
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
		return "/jobs (opens job gui)";
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
		if (!world.isRemote) {
			PacketManager.network.sendTo(new JobsCommandMessage(), player);
			player.sendMessage(new TextComponentString("[Server] : jobs gui opened"));
		}
		player.sendMessage(new TextComponentString("[Client] : jobs gui opened"));
	}
}
