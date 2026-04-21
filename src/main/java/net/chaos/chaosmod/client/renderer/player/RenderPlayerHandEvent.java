package net.chaos.chaosmod.client.renderer.player;

import net.chaos.chaosmod.config.ModConfig;
import net.chaos.chaosmod.init.ModKeybinds;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class RenderPlayerHandEvent {
	private static boolean rendering = false;

	@SubscribeEvent
	public static void onKeyInput(KeyInputEvent event) {
		pollTauntKeys();
	}

	@SideOnly(Side.CLIENT)
	private static void pollTauntKeys() {
		if (!ModConfig.CLIENT.areTauntsEnabled) return;

		int s = 20;
		if (ModKeybinds.tauntX.isPressed()) {
			ClientTauntManager.start(0, 5 * s);
		} else if (ModKeybinds.tauntY.isPressed()) {
			ClientTauntManager.start(1, 5 * s);
		} else if (ModKeybinds.tauntZ.isPressed()) {
			ClientTauntManager.start(2, 5 * s);
		}
	}

	@SubscribeEvent
	public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
		if (!ModConfig.CLIENT.areTauntsEnabled) return;

		if (rendering)
			return;

		rendering = true;
		renderCustomTaunts(event);

		rendering = false;
	}

	@SideOnly(Side.CLIENT)
	private static void renderCustomTaunts(Pre event) {
		event.setCanceled(true);

		RenderManager manager = event.getRenderer().getRenderManager();

		boolean useSmallArmsIn = event.getRenderer().smallArms;

		CustomRenderPlayer custom = new CustomRenderPlayer(manager, useSmallArmsIn);

		AbstractClientPlayer player = (AbstractClientPlayer) event.getEntityPlayer();

		double x = event.getX();
		double y = event.getY();
		double z = event.getZ();
		float yaw = player.rotationYaw;
		float partialTicks = event.getPartialRenderTick();

		custom.doRender(player, x, y, z, yaw, partialTicks);
	}
}