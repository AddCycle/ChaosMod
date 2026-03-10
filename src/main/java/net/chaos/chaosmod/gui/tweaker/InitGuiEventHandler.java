package net.chaos.chaosmod.gui.tweaker;

import net.chaos.chaosmod.sound.ClientSoundHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.ui.components.IconButtonBase;

@EventBusSubscriber(modid = Reference.MODID, value = Side.CLIENT)
public class InitGuiEventHandler {
	@SubscribeEvent
	public static void onGuiOpen(GuiOpenEvent event) {
//		if (event.getGui() instanceof GuiMainMenu && !(event.getGui() instanceof GuiChaosMath)) {
//			event.setGui(new GuiChaosMath());
//		}

		if (event.getGui() instanceof GuiMainMenu) {
//			Minecraft.getMinecraft().getSoundHandler().stopSounds();
//			playSound(ModSounds.BLACK_ROVER, 1.0f);
		}
	}

	@SubscribeEvent
	public static void onGuiInit(InitGuiEvent.Post event) {
		GuiScreen gui = event.getGui();

		if (gui instanceof GuiIngameMenu) {
			event.getButtonList().add(new IconButtonBase(99, gui.width - 20, 5, 15, 12));
		}
	}
	
	@SubscribeEvent
	public static void onButtonClick(ActionPerformedEvent.Post event) {
		GuiScreen gui = event.getGui();
		GuiButton button = event.getButton();
		
		if (gui instanceof GuiIngameMenu) {
			if (button.id == 99) {
				 event.getGui().mc.displayGuiScreen(new GuiConfig(gui, Reference.MODID, "ChaosMod Config Screen"));
			}
		}
	}
	
	// FIXME : playlist issue
	@SubscribeEvent
	public static void onSoundPlayed(PlaySoundEvent event) {
		System.out.println("played sound " + event.getName());
		System.out.println("tried to check current sound " + ClientSoundHandler.getSoundName());
		if (event.getName().equalsIgnoreCase(ClientSoundHandler.getSoundName())) {
	        if (ClientSoundHandler.isMusicPaused()) {
	            ClientSoundHandler.forcePauseMusic();
	        }
	    }
	}
	
	@SuppressWarnings("unused")
	private static void playSound(SoundEvent sound, float pitch) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(sound, pitch));
	}
}
