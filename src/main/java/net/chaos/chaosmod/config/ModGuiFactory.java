package net.chaos.chaosmod.config;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ModGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new GuiMainConfig(parentScreen);
    }

    private List<IConfigElement> getConfigElements() {
        Configuration config = ModConfig.getConfig();
        List<IConfigElement> elts = new ArrayList<IConfigElement>();
        for (String str : config.getCategoryNames()) {
        	List<IConfigElement> curr = new ConfigElement(config.getCategory(str)).getChildElements();
        	elts.addAll(curr);
        }
        return elts;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
}