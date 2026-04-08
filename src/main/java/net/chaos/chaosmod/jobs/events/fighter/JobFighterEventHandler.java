package net.chaos.chaosmod.jobs.events.fighter;

import java.util.function.Consumer;
import java.util.function.Predicate;

import net.chaos.chaosmod.jobs.TargetType;
import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.chaos.chaosmod.jobs.task.TaskType;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityTippedArrow;
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
		
		Predicate<LivingDeathEvent> tippedArrowKill = e -> e.getSource().getImmediateSource() instanceof EntityTippedArrow;
		processConditions(event, player, tippedArrowKill, "kill_with_tipped_arrow");
	}
	
	private static void processConditions(LivingDeathEvent event, EntityPlayerMP player, Predicate<LivingDeathEvent> predicate, String taskId) {
		onPlayerKillWithCondition(event, predicate,
			e -> JobEventUtils.incrementTask(player, JOB_ID, JobEventUtils.prefixId(taskId)
		));
		
	}

	private static void onPlayerKillWithCondition(LivingDeathEvent event, Predicate<LivingDeathEvent> predicate, Consumer<LivingDeathEvent> consumer) {
		if (predicate.test(event))
			consumer.accept(event);
	}
}