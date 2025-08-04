package net.chaos.chaosmod.gui;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.GuiScreen;

// TODO : refactor this class in order to load from JSON file ".chaosmod" all the credits and dialogs
public class GuiFinalCredits extends GuiScreen {
	private final List<Map.Entry<String, Integer>> creditsLines = new ArrayList<>();
    private int scroll;
    private int tickCounter = 0;
    private final int scrollDelay = 1;

    public GuiFinalCredits() {
        int blank = 0xFFFFFF;
        int blue = 0x00AEFF;
        int red = 0x970000;
        int orange = 0xFF7300;
        int green = 0x00AE00;

        // Preserve order using LinkedHashMap-like behavior
        creditsLines.add(new AbstractMap.SimpleEntry<>("Chaos Mod by AddCycle", blue));
        creditsLines.add(new AbstractMap.SimpleEntry<>("", blank));
        creditsLines.add(new AbstractMap.SimpleEntry<>("Inspired by madness, forged in pain.", blank));
        creditsLines.add(new AbstractMap.SimpleEntry<>("", blank));
        creditsLines.add(new AbstractMap.SimpleEntry<>("Thanks to:", blank));
        creditsLines.add(new AbstractMap.SimpleEntry<>("- JeanRobertPerez", red));
        creditsLines.add(new AbstractMap.SimpleEntry<>("- DjoDjo", orange));
        creditsLines.add(new AbstractMap.SimpleEntry<>("- People who suffered before me with FG3 and JEI initMissingTextures", blank));
        creditsLines.add(new AbstractMap.SimpleEntry<>("", blank));
        creditsLines.add(new AbstractMap.SimpleEntry<>("And you player, thanks a lot", blank));
        creditsLines.add(new AbstractMap.SimpleEntry<>("", blank));
        creditsLines.add(new AbstractMap.SimpleEntry<>("Check out my GitHub: AddCycle", blank));
        creditsLines.add(new AbstractMap.SimpleEntry<>("Rate it with a Star if you enjoyed it !", green));
        creditsLines.add(new AbstractMap.SimpleEntry<>("Support me on Ko-fi!", blank));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        int centerX = this.width / 2;
        int baseY = this.height - scroll;

        for (int i = 0; i < creditsLines.size(); i++) {
            String line = creditsLines.get(i).getKey();
            int color = creditsLines.get(i).getValue();
            drawCenteredString(this.fontRenderer, line, centerX, baseY + i * 20, color);
        }

        tickCounter++;
        if (tickCounter >= scrollDelay) {
            scroll++;
            tickCounter = 0;
        }
        // Close screen when last line scrolls past top
        if (baseY + creditsLines.size() * 20 < 0) {
            this.mc.displayGuiScreen(null);
        }
    }
    
    @Override
    public void onGuiClosed() {}

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
