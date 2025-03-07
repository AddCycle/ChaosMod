package net.chaos.chaosmod.commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.player.EntityPlayerMP;

public class TopCommand extends CommandBase {

	@Override
	public String getName() {
		return "top";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/top";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		// Entity entity = getEntity(server, sender, player.getName());
		// entity.setPosition(0, 64, 0);
		// entity.setPositionAndUpdate(0, 64, 0);
		BlockPos searcher_pos = new BlockPos(player.getPosition().getX(), 256, player.getPosition().getZ());
		Block searcher = player.getEntityWorld().getBlockState(searcher_pos).getBlock();
		while (searcher.isAssociatedBlock(Block.getBlockById(0))) {
			searcher_pos = searcher_pos.down();
			searcher = player.getEntityWorld().getBlockState(searcher_pos).getBlock();
		}
		int dimension = player.getEntityWorld().provider.getDimension();
		TeleportUtil.teleport(player, dimension, searcher_pos.getX(), searcher_pos.getY() + 1, searcher_pos.getZ());
	}
}
