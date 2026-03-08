package net.chaos.chaosmod.commands;

import java.util.Random;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.storage.loot.functions.Smelt;

public class FurnaceCommand extends CommandBase {

	@Override
	public String getName() {
		return "furnace";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/furnace";
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);
		ItemStack held = player.getHeldItemMainhand();
		ItemStack result = null;

		if (held != ItemStack.EMPTY) {
			Smelt smelter = new Smelt(null);
			result = smelter.apply(held, new Random(), null);
		}

		if (!held.isItemEqual(result)) {
			player.sendMessage(new TextComponentString(
					"Successfully melted : " + held.getDisplayName() + " into " + result.getDisplayName()));
			player.setHeldItem(EnumHand.MAIN_HAND, result);
			return;
		}

		player.sendMessage(new TextComponentString("Failed to melt : " + held.getDisplayName()));
	}
}
