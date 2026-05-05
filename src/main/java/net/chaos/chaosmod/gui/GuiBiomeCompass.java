package net.chaos.chaosmod.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.network.packets.PacketBiomeCompass;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.ui.components.GuiUtils;

@SideOnly(Side.CLIENT)
public class GuiBiomeCompass extends GuiScreen {
	private String biomeId;
	private GuiTextField trackedBiome;
	private GuiButton confirm;
	private GuiButton cancel;
	
	public GuiBiomeCompass() {}
	
	public GuiBiomeCompass(String trackedBiome) {
		biomeId = trackedBiome;
	}
	
	@Override
	public void initGui() {
        Keyboard.enableRepeatEvents(true);
		trackedBiome = new GuiTextField(2, this.fontRenderer, this.width / 2 - 150, 50, 300, 20);
		trackedBiome.setText(biomeId != null ? biomeId : "");
		confirm = addButton(new GuiButton(0, this.width / 2 - 100, 150, "Confirm"));
		cancel = addButton(new GuiButton(1, this.width / 2 - 100, 200, "Cancel"));
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if (button.id == 0) {
			String biomeRL = trackedBiome.getText();
			if (!biomeRL.isEmpty()) {
				PacketManager.network.sendToServer(new PacketBiomeCompass(biomeRL));
				GuiUtils.closeCurrentScreen(mc);
			}
		}

		if (button.id == 1) {
			GuiUtils.closeCurrentScreen(mc);
		}
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		drawCenteredString(fontRenderer, "Biome Compass", this.width / 2, 10, 0xffffffff);
		trackedBiome.drawTextBox();
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
		trackedBiome.mouseClicked(mouseX, mouseY, mouseButton);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		trackedBiome.textboxKeyTyped(typedChar, keyCode);
	}
	
	@Override
	public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
	}
}