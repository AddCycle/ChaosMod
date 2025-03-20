package util.handlers;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerInHandler {

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if (event.getWorld().isRemote) {
			event.getEntity().sendMessage(new TextComponentString("Bienvenue parmi nous!"));
			event.getEntity().sendMessage(new TextComponentString("Voici un tutoriel pour bien débuter"));
		}
	}
}
