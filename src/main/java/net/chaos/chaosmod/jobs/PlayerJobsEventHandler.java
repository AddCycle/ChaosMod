package net.chaos.chaosmod.jobs;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerJobsEventHandler {

	@SubscribeEvent
	/**
	 * Jobs using it : fighter, ...
	 * @param event
	 */
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

//		JobTask task = JobsManager.TASK_MANAGER.getTask(jobId, taskId);
		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		jobs.getProgress(jobId).incrementTask(player, jobId, taskId);
		
		Main.getLogger().info("Killed one zombie, incrementing task");
	}
}