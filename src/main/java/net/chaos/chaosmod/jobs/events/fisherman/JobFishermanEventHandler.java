package net.chaos.chaosmod.jobs.events.fisherman;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class JobFishermanEventHandler {
	private static Set<EntityPlayer> guiOpen = new HashSet<>();
	private static Map<EntityPlayer, Integer> delay = new HashMap<>();

	@SubscribeEvent
	public static void onPlayerTick(PlayerTickEvent event) {
		if (event.side.isClient()) return;

		EntityPlayer player = event.player;

		if (player.fishEntity != null && player.fishEntity.isInWater()) {
			if (guiOpen.contains(player)) return;

			if (!delay.containsKey(player)) {
				delay.put(player, 20);
				return;
			}

			int t = delay.get(player) - 1;

			if (t <= 0) {
	            player.openGui(Main.instance, Reference.GUI_FISHINGGAME_ID,
	                    player.world,
	                    (int) player.posX,
	                    (int) player.posY,
	                    (int) player.posZ);

				delay.remove(player);
	            guiOpen.add(player);
			} else {
				delay.put(player, t);
			}
		} else {
			delay.remove(player);
			guiOpen.remove(player);
		}
	}

	@SubscribeEvent
	public static void onPlayerFishing(ItemFishedEvent event) {
		if (event.getEntityPlayer().getEntityWorld().isRemote)
			return;

//		if (event.getDrops().stream().anyMatch(i -> i.getItem() instanceof ItemFishFood)) {
//			JobEventUtils.incrementTask((EntityPlayerMP) event.getEntityPlayer(), "fisherman", "first_catch");
//		}
	}
}