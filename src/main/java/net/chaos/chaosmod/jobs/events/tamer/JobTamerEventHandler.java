package net.chaos.chaosmod.jobs.events.tamer;

import java.util.Collection;

import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.TargetType;
import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

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

		Collection<JobTask> tasks = JobsManager.TASK_MANAGER.getTasks(JOB_ID);
		for (JobTask task : tasks) {
			if (task == null) continue;
			if (task.target == null) continue;

			if (task.type != TaskType.TAME || task.target.type != TargetType.ENTITY)
				continue;

			if (id.toString().equals(task.target.target)) {
				JobEventUtils.incrementTask((EntityPlayerMP) player, "tamer", task.id.split(":")[1]); // FIXME : most likely to create errors inconsistent name : choose between chaosmod:task or only task then add it later
			}
		}

	}
}