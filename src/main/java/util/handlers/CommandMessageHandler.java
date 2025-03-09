package util.handlers;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommandMessageHandler {

	@SubscribeEvent
	public void onCommandSent(CommandEvent event) {
		String name = event.getSender().getName();
		event.getSender().sendMessage(new TextComponentString(name + " avez lance la commande : " + event.getCommand().getName()));
	}
}
