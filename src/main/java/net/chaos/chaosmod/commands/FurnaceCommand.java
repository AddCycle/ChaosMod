package net.chaos.chaosmod.commands;

import java.util.Random;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentKeybind;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.storage.loot.functions.Smelt;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
		int quantity = held.getCount();
		// if (held != ItemStack.EMPTY && held.getItem()) {
		Smelt smelter = new Smelt(null);
		if (held != ItemStack.EMPTY) {
			result = smelter.apply(held, new Random(), null);
		}
		if (!held.isItemEqual(result)) {
			System.out.println("Successfully melted : " + held.getDisplayName() + " into " + result.getDisplayName());
			player.sendMessage(new TextComponentString("Successfully melted : " + held.getDisplayName() + " into " + result.getDisplayName()));
			player.setHeldItem(EnumHand.MAIN_HAND, result);
		} else if (held != null) {
			System.err.println("Failed to melt : " + held.getDisplayName());
			player.sendMessage(new TextComponentString("Failed to melt : " + held.getDisplayName()));
		}
		// @Deprecated : c'est juste des tests de methodes...
		/* if (held != ItemStack.EMPTY && held.isItemEqual(new ItemStack(ModBlocks.OXONIUM_ORE))) {
			player.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ModItems.OXONIUM, quantity));
			player.sendMessage(new TextComponentString("Command successfully launched with : " + held.getDisplayName()));
		} else if (held != ItemStack.EMPTY && held.isItemEqual(new ItemStack(ModBlocks.ALLEMANITE_ORE))) {
			player.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(ModItems.ALLEMANITE_INGOT, quantity));
			player.sendMessage(new TextComponentString("Command successfully launched with : " + held.getDisplayName()));
		} else {
			player.sendMessage(new TextComponentString("Error, currently working only with oxonium_ore -> oxonium_ingots"
				+ " && allemanite_ore -> allemanite_ingots"));
		}*/
		System.out.println("Currently holding: " + held.getDisplayName());
	}

}
