package net.chaos.chaosmod.commands.overrides;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class CommandEventHandler {

	@SubscribeEvent
	public static void onCommandExecuted(CommandEvent event) {
		replaceCommand("locate", new LocateCommandReplacement(), event);
	}

	private static void replaceCommand(String commandName, ICommand newCommand, CommandEvent event) {
		if (!matching(event, commandName)) return;

		ICommandSender sender = event.getSender();
		try {
			newCommand.execute(sender.getServer(), sender, event.getParameters());
		} catch (CommandException e) {
			sender.sendMessage(new TextComponentTranslation(e.getMessage()));
		}
		
		event.setCanceled(true);
	}

	private static boolean matching(CommandEvent event, String name) {
		return event.getCommand().getName().equalsIgnoreCase(name);
	}
}