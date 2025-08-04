package net.chaos.chaosmod.config;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiMainConfig extends GuiScreen {
    private final GuiScreen parent;

    public GuiMainConfig(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        int x = this.width / 2 - 100;
        int y = this.height / 4;

        this.buttonList.add(new GuiButton(0, x, y, "Client Settings"));
        this.buttonList.add(new GuiButton(1, x, y + 24, "Server Settings"));
        this.buttonList.add(new GuiButton(2, x, y + 60, "Back"));
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new GuiClientConfig(this));
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiServerConfig(this));
                break;
            case 2:
                this.mc.displayGuiScreen(parent);
                break;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        drawCenteredString(this.fontRenderer, "ChaosMod Config", this.width / 2, 15, 0xFFFFFF);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}