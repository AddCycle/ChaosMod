package net.chaos.chaosmod.jobs.events;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.minecraft.entity.player.EntityPlayerMP;
import util.Reference;

public class JobEventUtils {

	/**
	 * The system is done a way that the task progress is incremented by 1, capping at goal and syncs capability jobs
	 * @param player
	 * @param jobid
	 * @param taskId
	 */
	public static void incrementTask(EntityPlayerMP player, String jobid, String taskId) {
		String jobId = prefixId(jobid);

		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs == null)
			return;

		jobs.getProgress(jobId).incrementTask(player, jobId, prefixId(taskId));

		Main.getLogger().info("Job done, incrementing task: [{}:{}]", jobid, taskId);
	}

	public static String prefixId(String id) {
		return Reference.PREFIX + id;
	}

}