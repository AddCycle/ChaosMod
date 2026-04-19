package net.chaos.chaosmod.commands.debug;

import java.util.Collections;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.server.command.CommandSetDimension;

public class TpDimCommand extends CommandSetDimension {

	@Override
	public String getName() {
		return "tpdim";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Use /tpdim <player> <dimension_id> [x] [y] [z]";
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
			BlockPos targetPos) {
		if (args.length == 1) return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());

        if (args.length > 2 && args.length <= 5)
        {
            return getTabCompletionCoordinate(args, 2, targetPos);
        }
        return Collections.emptyList();
	}


	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		super.execute(server, sender, args);
	}
}