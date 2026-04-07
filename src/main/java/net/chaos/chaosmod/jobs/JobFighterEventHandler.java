package net.chaos.chaosmod.jobs;

import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class JobFighterEventHandler {
	private static final String JOB_ID = Reference.PREFIX + "fighter";

	@SubscribeEvent
	public static void onPlayerKillsEntity(LivingDeathEvent event) {
		if (event.getEntity().getEntityWorld().isRemote)
			return;

		if (!(event.getSource().getTrueSource() instanceof EntityPlayer))
			return;

		EntityPlayerMP player = (EntityPlayerMP) event.getSource().getTrueSource();
		ResourceLocation id = EntityList.getKey(event.getEntity());
		if (id == null) return;
		
		JobEventUtils.incrementRelatedMatchingTasks(id, TaskType.KILL, TargetType.ENTITY, JOB_ID, player);
	}
}