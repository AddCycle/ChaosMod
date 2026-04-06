package net.chaos.chaosmod.jobs;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.entity.EntityPicsou;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
// TODO : split the class into fighter/hunter jobs handler (LATER make the json handle types like the criterions in achievements)
// for example "entity": "entity_id", "type": "tame/hunt/kill"...
public class PlayerJobsEventHandler {

	/**
	 * Jobs using it : fighter, ...
	 * @param event
	 */
	@SubscribeEvent
	public static void onPlayerKillsEntity(LivingDeathEvent event) {
		if (event.getEntity().getEntityWorld().isRemote)
			return;

		if (!(event.getSource().getTrueSource() instanceof EntityPlayer))
			return;

		EntityPlayerMP player = (EntityPlayerMP) event.getSource().getTrueSource();
		Entity entityKilled = event.getEntity();
		
		processZombieKills(player, entityKilled);
		processPicsouKills(player, entityKilled);
	}

	private static void processPicsouKills(EntityPlayerMP player, Entity entityKilled) {
		if (!(entityKilled instanceof EntityPicsou)) return;
		
		String jobId = prefixId("fighter");
		String taskId = prefixId("second_kill");

		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs == null) return;

		jobs.getProgress(jobId).incrementTask(player, jobId, taskId);
		
		Main.getLogger().info("Killed picsou, incrementing task");
	}
	
	private static void processZombieKills(EntityPlayerMP player, Entity entityKilled) {
		if (!(entityKilled instanceof EntityZombie)) return;
		
		String jobId = prefixId("fighter");
		String taskId = prefixId("first_kill");

		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs == null) return;

		JobProgress progress = jobs.getProgress(jobId);
		progress.incrementTask(player, jobId, taskId);
		
		Main.getLogger().info("Killed one zombie, incrementing task");
	}
	
	private static String prefixId(String id) {
		return Reference.PREFIX + id;
	}
}