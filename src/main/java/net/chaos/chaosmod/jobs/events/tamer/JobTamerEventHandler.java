package net.chaos.chaosmod.jobs.events.tamer;

import net.chaos.chaosmod.jobs.events.JobEventUtils;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class JobTamerEventHandler {

	@SubscribeEvent
	public static void onTamedAnimal(AnimalTameEvent event) {
		EntityPlayer player = event.getTamer();
		if (player.world.isRemote) return;

		if (event.getAnimal() instanceof EntityWolf) {
			JobEventUtils.incrementTask((EntityPlayerMP) player, "tamer", "tame_wolf");
		}
	}
}