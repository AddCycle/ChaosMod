package net.chaos.chaosmod.cutscene;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class CutsceneEvents {
	@SubscribeEvent
	public static void onClientTick(TickEvent.ClientTickEvent event) {
		if (event.phase != TickEvent.Phase.END) return;

		CutsceneManager.onClientTick();
	}

	@SubscribeEvent
	public static void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
		if (CutsceneManager.isPlaying()) {
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onRenderHand(RenderHandEvent event) {
		if (CutsceneManager.isPlaying()) {
			event.setCanceled(true);
		}
	}
}