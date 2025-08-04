package net.chaos.chaosmod.config;

import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiClientConfig extends GuiConfig {
	public GuiClientConfig(GuiScreen parent) {
		super(parent, getConfigElements(), "chaosmod", false, false, "Client Configuration");
	}

	private static List<IConfigElement> getConfigElements() {
		return new ConfigElement(ModConfig.getConfig().getCategory("client")).getChildElements();
	}
}
