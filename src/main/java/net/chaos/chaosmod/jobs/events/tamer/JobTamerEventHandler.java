package net.chaos.chaosmod.jobs.events.tamer;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.jobs.TargetType;
import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

// FIXME
@EventBusSubscriber(modid = Reference.MODID)
public class JobTamerEventHandler {
	public static final String JOB_ID = Reference.PREFIX + "tamer";

	@SubscribeEvent
	public static void onTamedAnimal(AnimalTameEvent event) {
		EntityPlayer player = event.getTamer();
		if (player.world.isRemote)
			return;

		ResourceLocation id = EntityList.getKey(event.getAnimal());
		if (id == null)
			return;

		JobEventUtils.incrementRelatedMatchingTasks(id, TaskType.TAME, TargetType.ENTITY, JOB_ID, player);
		
		Main.getLogger().info("player tamed : {}", id.toString());
	}	
}