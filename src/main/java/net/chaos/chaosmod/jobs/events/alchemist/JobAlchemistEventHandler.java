package net.chaos.chaosmod.jobs.events.alchemist;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.jobs.TargetType;
import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.brewing.PlayerBrewedPotionEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class JobAlchemistEventHandler {
	private static final String JOB_ID = Reference.PREFIX + "alchemist";

	/**
	 * The event is fired server-side by forge so no need to check sides
	 * @param event
	 */
	@SubscribeEvent
	public static void onPotionBrew(PlayerBrewedPotionEvent event) {
		PotionType type = PotionUtils.getPotionFromItem(event.getStack());
		ResourceLocation rl = ForgeRegistries.POTION_TYPES.getKey(type);
		
		if (rl == null) return;
		
		Main.getLogger().info("PotionType : {}", rl.toString());

		JobEventUtils.incrementRelatedMatchingTasks(rl, TaskType.BREW, TargetType.POTION, JOB_ID, event.getEntityPlayer());
	}
}
