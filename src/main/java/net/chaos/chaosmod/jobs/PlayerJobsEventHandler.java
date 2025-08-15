package net.chaos.chaosmod.jobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PlayerJobsEventHandler {

	@SubscribeEvent
	public void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
	    if (event.getObject() instanceof EntityPlayer) {
	        PlayerJobs jobs = new PlayerJobs();
	        event.addCapability(CapabilityPlayerJobs.KEY, new ICapabilitySerializable<NBTTagCompound>() {
	            @Override
	            public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
	                return capability == CapabilityPlayerJobs.PLAYER_JOBS;
	            }

	            @SuppressWarnings("unchecked")
	            @Override
	            public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
	                return capability == CapabilityPlayerJobs.PLAYER_JOBS ? (T) jobs : null;
	            }

	            @Override
	            public NBTTagCompound serializeNBT() {
	                return jobs.serializeNBT();
	            }

	            @Override
	            public void deserializeNBT(NBTTagCompound nbt) {
	                jobs.deserializeNBT(nbt);
	            }
	        });
	    }
	}
	
	@SubscribeEvent
	public static void onPlayerClone(PlayerEvent.Clone event) {
	    if (!event.isWasDeath()) return; // optionally copy on death too

	    PlayerJobs oldJobs = event.getOriginal().getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
	    PlayerJobs newJobs = event.getEntityPlayer().getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);

	    if (oldJobs != null && newJobs != null) {
	        newJobs.deserializeNBT(oldJobs.serializeNBT());
	    }
	}

}