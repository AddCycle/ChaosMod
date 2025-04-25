package net.chaos.chaosmod.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;

public class GuiFinalCredits extends GuiScreen {
	private final List<String> creditsLines = new ArrayList<>();
    private int scroll;

    public GuiFinalCredits() {
        creditsLines.add("Chaos Mod by AddCycle");
        creditsLines.add("");
        creditsLines.add("Inspired by madness, forged in pain.");
        creditsLines.add("");
        creditsLines.add("Thanks to:");
        creditsLines.add("- JeanRobertPerez");
        creditsLines.add("- DjoDjo");
        creditsLines.add("- People who suffered before me with FG3 & JEI initMissingTextures");
        creditsLines.add("");
        creditsLines.add("And you, player thanks a lot");
        creditsLines.add("");
        creditsLines.add("Check out my Github AddCycle, and please support-me on Ko-fi !");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        int centerX = this.width / 2;
        int baseY = this.height - scroll;

        for (int i = 0; i < creditsLines.size(); i++) {
            String line = creditsLines.get(i);
            drawCenteredString(this.fontRenderer, line, centerX, baseY + i * 20, 0xFFFFFF);
        }

        scroll += 1;
        if (baseY + creditsLines.size() * 20 < 0) {
            this.mc.displayGuiScreen(null); // Close screen when finished
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
