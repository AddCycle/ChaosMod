package net.chaos.chaosmod.jobs;

import net.chaos.chaosmod.network.PacketManager;
import net.chaos.chaosmod.network.PacketSyncPlayerJobs;
import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerJobsManager {

	public void addExp(EntityPlayerMP player, String jobId, int amount) {
	    PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
	    if (jobs != null) {
	        jobs.addExp(jobId, amount);

	        // Send updated data to client
	        PacketManager.network.sendTo(
	            new PacketSyncPlayerJobs(jobs),
	            player
	        );
	    }
	}

}
