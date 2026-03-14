package net.chaos.chaosmod.jobs;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerJobsEventHandler {

	@SubscribeEvent
	public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			PlayerJobs jobs = new PlayerJobs();
			event.addCapability(CapabilityPlayerJobs.JOBS_CAPABILITY_ID, new ICapabilitySerializable<NBTTagCompound>() {
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
		if (!event.isWasDeath())
			return;

		PlayerJobs oldJobs = event.getOriginal().getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		PlayerJobs newJobs = event.getEntityPlayer().getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);

		if (oldJobs != null && newJobs != null) {
			newJobs.deserializeNBT(oldJobs.serializeNBT());
		}
	}

	@SubscribeEvent
	public static void onPlayerKillsEntity(LivingDeathEvent event) {
		if (event.getEntity().getEntityWorld().isRemote)
			return;

		if (!(event.getSource().getTrueSource() instanceof EntityPlayer))
			return;

		EntityPlayerMP player = (EntityPlayerMP) event.getSource().getTrueSource();
		Entity entityKilled = event.getEntity();
		
		if (!(entityKilled instanceof EntityZombie)) return;
		
		String jobId = Reference.PREFIX + "fighter";
		String taskId = Reference.PREFIX + "first_kill";

		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		jobs.getProgress(jobId).incrementTask(player, jobId, taskId);
		
		Main.getLogger().info("Killed one zombie, incrementing task");
	}
}