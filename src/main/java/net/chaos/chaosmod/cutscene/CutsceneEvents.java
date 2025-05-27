package net.chaos.chaosmod.cutscene;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CutsceneEvents {
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
	    if (event.phase == TickEvent.Phase.END) {
	    	// System.out.println("TRIGGER EVENT : clientTick");
	        CutsceneManager.onClientTick();
	    }
	}

	@SubscribeEvent
	public void onRenderOverlay(RenderGameOverlayEvent.Pre event) {
		// System.out.println("TRIGGER EVENT : render overlay");
	    if (CutsceneManager.isPlaying()) {
	        event.setCanceled(true);
	    }
	}
	
	@SubscribeEvent
	public void onRenderHand(RenderHandEvent event) {
	    if (CutsceneManager.isPlaying()) {
	        event.setCanceled(true);
	    }
	}
}
