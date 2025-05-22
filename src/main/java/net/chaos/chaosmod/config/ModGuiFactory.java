package net.chaos.chaosmod.config;

import java.io.File;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import util.Reference;

public class ModGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiConfig createConfigGui(GuiScreen parentScreen) {
        return new GuiConfig(parentScreen, getConfigElements(), Reference.MODID, false, false, "ChaosMod config");
    }

    private List<IConfigElement> getConfigElements() {
        // Configuration config = new Configuration(new File(Minecraft.getMinecraft().mcDataDir, "config/chaosmod.cfg"));
        Configuration config = ModConfig.getConfig();
        return new ConfigElement(config.getCategory(Configuration.CATEGORY_CLIENT)).getChildElements();
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
}