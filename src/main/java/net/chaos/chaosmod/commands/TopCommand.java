package net.chaos.chaosmod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import util.dimensions.TeleportUtil;

public class TopCommand extends CommandBase {

	@Override
	public String getName() {
		return "top";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/top [player]";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length > 0) {
			EntityPlayerMP player = getPlayer(server, sender, args[0]);
			processTop(player);
			return;
		}
		
		EntityPlayerMP self = getCommandSenderAsPlayer(sender);
		processTop(self);
	}

	private void processTop(EntityPlayerMP player) {
		World world = player.getEntityWorld();

		BlockPos pos = player.getPosition();
		int height = world.getHeight(pos.getX(), pos.getZ());

		if (pos.getY() < height)
			TeleportUtil.teleportCenter(player, pos.getX(), height, pos.getZ());
	}
}