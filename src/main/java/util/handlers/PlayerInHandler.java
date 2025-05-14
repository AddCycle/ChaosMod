package util.handlers;

import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.init.ModCapabilities;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

public class PlayerInHandler {

	@SubscribeEvent
	public void onJoin(EntityJoinWorldEvent event) {
		if (!event.getWorld().isRemote) {
			event.getEntity().sendMessage(new TextComponentString("Bienvenue parmi nous!"));
			event.getEntity().sendMessage(new TextComponentString("Voici un tutoriel pour bien débuter"));
		}
	}
	
	@SubscribeEvent
	public void onPlayerClone(PlayerEvent.Clone event) {
		if (!event.getOriginal().getEntityWorld().isRemote) {
			event.getEntityPlayer().getEntityData().setIntArray(Reference.MODID + "_homepos", event.getOriginal().getEntityData().getIntArray(Reference.MODID + "_homepos"));
		}

		IAccessory oldCap = event.getOriginal().getCapability(ModCapabilities.ACCESSORY, null);
	    IAccessory newCap = event.getEntityPlayer().getCapability(ModCapabilities.ACCESSORY, null);

	    if (oldCap != null && newCap != null) {
	        newCap.setAccessoryItem(oldCap.getAccessoryItem().copy());
	    }
	}
}
