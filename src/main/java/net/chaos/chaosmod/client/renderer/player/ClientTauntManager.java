package net.chaos.chaosmod.client.renderer.player;

public class ClientTauntManager {
	public static int taunt = -1;
	public static int tauntTicks;
	
	public static void start(int taunt, int ticks) {
		ClientTauntManager.taunt = taunt;
		tauntTicks = ticks;
	}
	
	public static void update() {
		if (isTaunting())
			tauntTicks--;
		else
			stop();
	}
	
	public static void stop() {
		taunt = -1;
	}
	
	public static boolean isTaunting() {
		return tauntTicks > 0;
	}
}