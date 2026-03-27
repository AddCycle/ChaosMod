package net.chaos.chaosmod.world.events;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.init.ModKeybinds;
import net.chaos.chaosmod.jobs.gui.GuiScreenJobs;
import net.chaos.chaosmod.network.packets.PacketGameMode;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.GameType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class KeypressEventHandler {
	public static int clientMode = 0;

	@SubscribeEvent
	public static void onKeyPressed(KeyInputEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		if (ModKeybinds.displayJobsKey.isPressed()) {
			if (mc.currentScreen == null) {
				mc.displayGuiScreen(new GuiScreenJobs());
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
			if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
				clientMode++;
				clientMode %= GameType.values().length - 1;
				PacketManager.network.sendToServer(new PacketGameMode(clientMode));
				mc.player.sendMessage(new TextComponentString("gameMode: " + GameType.getByID(clientMode).getName()));
			}
		}
	}
}