package util.handlers;

import java.util.Set;

import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.network.PacketAccessorySync;
import net.chaos.chaosmod.network.PacketManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import util.Reference;

public class PlayerInHandler {

	@SubscribeEvent
	/*
	 * Sync players accessories on joining server
	 */
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
	    if (!player.world.isRemote) {
	        IAccessory cap = player.getCapability(ModCapabilities.ACCESSORY, null);
	        if (cap != null) {
	            ItemStack stack = cap.getAccessoryItem();
	            PacketManager.network.sendTo(new PacketAccessorySync(player, stack), (EntityPlayerMP) player);
	        }
	    }
	}
	
	@SubscribeEvent
	/*
	 * When the player RESPAWN event is fired to prevent duplicates on server
	 */

	// prevents issues with homes & accessories
	public void onPlayerClone(PlayerEvent.Clone event) {
		if (!event.getOriginal().getEntityWorld().isRemote) {
			String homesNumberKey = Reference.PREFIX + "homesNumber";
			Set<String> homeSet = event.getOriginal().getEntityData().getKeySet();
			homeSet.forEach(key -> {
				if (key.equalsIgnoreCase(homesNumberKey)) {
					event.getEntityPlayer().getEntityData().setInteger(key, event.getOriginal().getEntityData().getInteger(key));
				} else
				if (key.startsWith(Reference.PREFIX) && (key.endsWith("_homepos") || key.endsWith("prevhomepos"))) {
					event.getEntityPlayer().getEntityData().setIntArray(key, event.getOriginal().getEntityData().getIntArray(key));
				}
			});

			// prevents issues with patchouli book gift
			NBTTagCompound origEntityData = event.getOriginal().getEntityData();
	        NBTTagCompound newEntityData = event.getEntityPlayer().getEntityData();

	        NBTTagCompound origPersist;
	        if (origEntityData.hasKey(EntityPlayer.PERSISTED_NBT_TAG, 10)) {
	            origPersist = origEntityData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
	        } else {
	            origPersist = new NBTTagCompound();
	            origEntityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, origPersist);
	        }

	        NBTTagCompound newPersist;
	        if (newEntityData.hasKey(EntityPlayer.PERSISTED_NBT_TAG, 10)) {
	            newPersist = newEntityData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
	        } else {
	            newPersist = new NBTTagCompound();
	            newEntityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, newPersist);
	        }

	        String key = Reference.PREFIX + "first_join";
	        if (origPersist.hasKey(key)) {
	            newPersist.setBoolean(key, origPersist.getBoolean(key));
	        }

	        newEntityData.setTag(EntityPlayer.PERSISTED_NBT_TAG, newPersist);
		}

		// prevents issues with this mod necklaces
		IAccessory oldCap = event.getOriginal().getCapability(ModCapabilities.ACCESSORY, null);
	    IAccessory newCap = event.getEntityPlayer().getCapability(ModCapabilities.ACCESSORY, null);

	    if (oldCap != null && newCap != null) {
	        newCap.setAccessoryItem(oldCap.getAccessoryItem().copy());
	    }
	}
}
