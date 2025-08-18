package net.chaos.chaosmod.gui.tweaker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class InitGuiEventHandler {

	@SubscribeEvent
	public static void onGuiInit(InitGuiEvent.Post event) {
		GuiScreen gui = event.getGui();
		if (gui instanceof GuiIngameMenu) {
			event.getButtonList().add(new GuiButton(99, gui.width - 20, 0, 20, 20, "C"));
		}
	}
	
	@SubscribeEvent
	public static void onButtonClick(ActionPerformedEvent.Post event) {
		GuiScreen gui = event.getGui();
		GuiButton button = event.getButton();
		
		if (gui instanceof GuiIngameMenu) {
			if (button.id == 99) {
				 Minecraft.getMinecraft().displayGuiScreen(new GuiConfig(gui, Reference.MODID, "ChaosMod Config Screen"));
			}
		}
	}
}
