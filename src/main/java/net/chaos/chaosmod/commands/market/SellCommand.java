package net.chaos.chaosmod.commands.market;

import net.chaos.chaosmod.commands.AbstractPermissionFreeCommand;
import net.chaos.chaosmod.market.MarketData;
import net.chaos.chaosmod.market.MarketDataHandler;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class SellCommand extends AbstractPermissionFreeCommand {

	@Override
	public String getName() {
		return "sell";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/sell";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayer player = getCommandSenderAsPlayer(sender);
		World world = player.getEntityWorld();
		MarketData data = MarketDataHandler.get(world);
		// TODO
	}

}
