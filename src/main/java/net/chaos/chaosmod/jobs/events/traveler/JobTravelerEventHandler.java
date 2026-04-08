package net.chaos.chaosmod.jobs.events.traveler;

import net.chaos.chaosmod.jobs.TargetType;
import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import util.Reference;

/**
 * This class is for handling chaosmod:traveler job
 */
//@EventBusSubscriber(modid = Reference.MODID)
public class JobTravelerEventHandler {
	private static final String JOB_ID = Reference.PREFIX + "traveler";

	// make the method return false to cancel the task
	public static boolean onPlayerDiscoversBiome(EntityPlayer player, Biome biome) {
		ResourceLocation biomeId = biome.getRegistryName();
		if (biomeId == null) return false;
		
		JobEventUtils.incrementRelatedMatchingTasks(biomeId, TaskType.TRAVEL, TargetType.BIOME, JOB_ID, player);
		
		return true;
	}
}