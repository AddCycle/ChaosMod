package net.chaos.chaosmod.jobs.events.fisherman;

import java.util.HashMap;
import java.util.Map;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFishFood;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
// TODO : modify the fishing loot tables by overriding (forge overrides) the directory assets/lootables/... of vanilla mc
public class JobFishermanEventHandler {
	private static Map<EntityPlayerMP, Integer> delay = new HashMap<>();

	@SubscribeEvent
	// TODO : next make the fishing minigame & a gui associated
	public static void onPlayerTick(PlayerTickEvent event) {
		if (event.side.isClient())
			return;

		EntityPlayerMP player = (EntityPlayerMP) event.player;

		if (player.fishEntity != null && player.fishEntity.isInWater()) {
			if (!delay.containsKey(player)) {
	            delay.put(player, 20);
	            return;
	        }

	        int t = delay.get(player) - 1;

	        if (t <= 0) {
	            player.openGui(Main.instance, Reference.GUI_CREDITS_ID,
	                    player.world,
	                    (int) player.posX,
	                    (int) player.posY,
	                    (int) player.posZ);

	            player.fishEntity.setDead();
	            delay.remove(player);

	        } else {
	            delay.put(player, t);
	        }
		}

		if (player.fishEntity == null) {
		    delay.remove(player);
		}
	}

	@SubscribeEvent
	public static void onPlayerFishing(ItemFishedEvent event) {
		if (event.getEntityPlayer().getEntityWorld().isRemote)
			return;

		if (event.getDrops().stream().anyMatch(i -> i.getItem() instanceof ItemFishFood)) {
			incrementFirstCatchTask((EntityPlayerMP) event.getEntityPlayer());
		}
	}

	private static void incrementFirstCatchTask(EntityPlayerMP player) {
		String jobId = prefixId("fisherman");
		String taskId = prefixId("first_catch");

		PlayerJobs jobs = player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
		if (jobs == null)
			return;

		jobs.getProgress(jobId).incrementTask(player, jobId, taskId);

		Main.getLogger().info("Caught fish, incrementing task");
	}

	private static String prefixId(String id) {
		return Reference.PREFIX + id;
	}
}