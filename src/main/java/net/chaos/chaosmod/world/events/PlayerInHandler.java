package net.chaos.chaosmod.world.events;

import java.util.Set;

import net.chaos.chaosmod.client.inventory.IAccessory;
import net.chaos.chaosmod.common.capabilities.accessory.CapabilityAccessory;
import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.jobs.task.SharedTaskProgress;
import net.chaos.chaosmod.network.packets.PacketAccessorySync;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketSyncJobs;
import net.chaos.chaosmod.network.packets.PacketSyncPlayerJobs;
import net.chaos.chaosmod.network.packets.PacketSyncSharedTasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import util.Reference;
import util.blockstates.BlockHelper;

// TODO: REFACTOR this class
@EventBusSubscriber(modid = Reference.MODID)
public class PlayerInHandler {

	@SubscribeEvent
	/**
	 * Sync players accessories on joining server
	 * 
	 * @param event
	 */
	public static void onPlayerJoin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;

		if (player.world.isRemote)
			return;

		IAccessory cap = player.getCapability(CapabilityAccessory.ACCESSORY, null);
		if (cap != null) {
			ItemStack stack = cap.getAccessoryItem();
			PacketManager.network.sendTo(new PacketAccessorySync(player, stack), (EntityPlayerMP) player);
		}

		// Sending jobs data to client
		String jsonData = JobsManager.toJsonString(); // serialize all jobs
		PacketManager.network.sendTo(new PacketSyncJobs(jsonData), (EntityPlayerMP) event.player);
		syncJobCapabilities((EntityPlayerMP) event.player);

		syncJobsWorldData(event);
	}

	@SubscribeEvent
	public static void onPlayerDisconnect(PlayerLoggedOutEvent event) {
		if (event.player.world.isRemote)
			return;

		EntityPlayerMP player = (EntityPlayerMP) event.player;
		if (player.getEntityData().hasKey("sword_of_wrath_cast")
				&& player.getEntityData().getBoolean("sword_of_wrath_cast")) {
			player.addItemStackToInventory(new ItemStack(ModItems.SWORD_OF_WRATH_CASTER));
			player.getEntityData().setBoolean("sword_of_wrath_cast", false);
		}
	}

	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {

		syncAccessories(event);

		if (event.getOriginal().getEntityWorld().isRemote)
			return;

		String homeCountKey = Reference.PREFIX + "homesNumber";
		Set<String> homeSet = event.getOriginal().getEntityData().getKeySet();
		homeSet.forEach(key ->
		{
			if (key.equalsIgnoreCase(homeCountKey)) {
				event.getEntityPlayer().getEntityData().setInteger(key,
						event.getOriginal().getEntityData().getInteger(key));
			} else if (key.startsWith(Reference.PREFIX) && (key.endsWith("_homepos") || key.endsWith("prevhomepos"))) {
				event.getEntityPlayer().getEntityData().setIntArray(key,
						event.getOriginal().getEntityData().getIntArray(key));
			}
		});

		// prevents issues with patchouli book gift
		NBTTagCompound originalData = event.getOriginal().getEntityData();
		NBTTagCompound clonedData = event.getEntityPlayer().getEntityData();

		NBTTagCompound originalPersistentData;
		if (originalData.hasKey(EntityPlayer.PERSISTED_NBT_TAG, 10)) {
			originalPersistentData = originalData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		} else {
			originalPersistentData = new NBTTagCompound();
			originalData.setTag(EntityPlayer.PERSISTED_NBT_TAG, originalPersistentData);
		}

		NBTTagCompound newPersistentData;
		if (clonedData.hasKey(EntityPlayer.PERSISTED_NBT_TAG, 10)) {
			newPersistentData = clonedData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		} else {
			newPersistentData = new NBTTagCompound();
			clonedData.setTag(EntityPlayer.PERSISTED_NBT_TAG, newPersistentData);
		}

		String key = Reference.PREFIX + "first_join";
		if (originalPersistentData.hasKey(key)) {
			newPersistentData.setBoolean(key, originalPersistentData.getBoolean(key));
		}

		if (event.isWasDeath()) {
			String deathKey = Reference.PREFIX + "lastDeathPosition";
			BlockPos deathPos = event.getOriginal().getPosition();

			newPersistentData.setIntArray(deathKey, BlockHelper.getPosArray(deathPos));
		}

		clonedData.setTag(EntityPlayer.PERSISTED_NBT_TAG, newPersistentData);
	}

	public static void syncJobCapabilities(EntityPlayerMP player) {
		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		PacketManager.network.sendTo(new PacketSyncPlayerJobs(jobs), player);
	}

	private static void syncJobsWorldData(PlayerLoggedInEvent event) {
		if (!(event.player instanceof EntityPlayerMP)) return;

		EntityPlayerMP player = (EntityPlayerMP) event.player;
		SharedTaskProgress sharedProgress = SharedTaskProgress.get(player.getEntityWorld());
		if (sharedProgress != null) {
			PacketManager.network.sendTo(new PacketSyncSharedTasks(sharedProgress), player);
		}
	}

	private static void syncAccessories(Clone event) {
		// prevents issues with necklaces items
		IAccessory oldCap = event.getOriginal().getCapability(CapabilityAccessory.ACCESSORY, null);
		IAccessory newCap = event.getEntityPlayer().getCapability(CapabilityAccessory.ACCESSORY, null);

		if (oldCap != null && newCap != null) {
			newCap.setAccessoryItem(oldCap.getAccessoryItem().copy());
		}
	}
}
