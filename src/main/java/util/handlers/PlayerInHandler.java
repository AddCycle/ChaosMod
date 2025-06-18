package util.handlers;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.network.PacketAccessorySync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import util.Reference;

public class PlayerInHandler {

	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
	    if (!player.world.isRemote) {
	        IAccessory cap = player.getCapability(ModCapabilities.ACCESSORY, null);
	        if (cap != null) {
	            ItemStack stack = cap.getAccessoryItem();
	            Main.network.sendTo(new PacketAccessorySync(player, stack), (EntityPlayerMP) player);
	        }
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
