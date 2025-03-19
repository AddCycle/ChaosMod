package util.handlers;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BlockPlaceHandler {

	@SubscribeEvent
	public void onPlaceEvent(EntityPlaceEvent event) {
		event.getEntity().sendMessage(new TextComponentString("Placed block event : " + event.getPlacedBlock().getBlock().getLocalizedName()));
	}
	
	/*@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		event.getEntity().sendMessage(new TextComponentString("Bienvenue parmi nous!"));
	}*/
}