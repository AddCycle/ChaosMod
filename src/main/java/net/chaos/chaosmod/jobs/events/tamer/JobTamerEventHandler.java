package net.chaos.chaosmod.jobs.events.tamer;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class JobTamerEventHandler {

	@SubscribeEvent
	public static void onTamedAnimal(AnimalTameEvent event) {
		EntityPlayer player = event.getTamer();
		if (player.world.isRemote) return;

		if (event.getAnimal() instanceof EntityWolf) {
			incrementTask((EntityPlayerMP) player, "tame_wolf");
		}
	}

	private static void incrementTask(EntityPlayerMP player, String taskId) {
		String jobId = prefixId("tamer");

		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs == null)
			return;

		jobs.getProgress(jobId).incrementTask(player, jobId, prefixId(taskId));

		Main.getLogger().info("Caught fish, incrementing task");
	}

	private static String prefixId(String id) {
		return Reference.PREFIX + id;
	}
}