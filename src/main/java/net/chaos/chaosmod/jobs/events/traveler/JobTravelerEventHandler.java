package net.chaos.chaosmod.jobs.events.traveler;

import net.chaos.chaosmod.jobs.TargetType;
import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

/**
 * This class is for handling chaosmod:traveler job
 */
@EventBusSubscriber(modid = Reference.MODID)
public class JobTravelerEventHandler {
	private static final String JOB_ID = Reference.PREFIX + "traveler";

	/**
	 * Make the method return false to cancel the task
	 * @param player
	 * @param biome
	 * @return
	 */
	public static boolean onPlayerDiscoversBiome(EntityPlayer player, Biome biome) {
		ResourceLocation biomeId = biome.getRegistryName();
		if (biomeId == null) return false;
		
		JobEventUtils.incrementRelatedMatchingTasks(biomeId, TaskType.TRAVEL, TargetType.BIOME, JOB_ID, player);
		
		return true;
	}
	
	@SubscribeEvent
	public static void onTravelToDimension(EntityTravelToDimensionEvent event) {
		if (!(event.getEntity() instanceof EntityPlayerMP)) return;
		EntityPlayerMP player = (EntityPlayerMP) event.getEntity();

		DimensionType type = DimensionType.getById(event.getDimension());
		JobEventUtils.incrementRelatedMatchingTasks(new ResourceLocation("dim", type.getName()), TaskType.TRAVEL, TargetType.DIMENSION, JOB_ID, player);
	}
}