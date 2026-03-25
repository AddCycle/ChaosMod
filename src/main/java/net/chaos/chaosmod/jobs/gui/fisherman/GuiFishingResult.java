package net.chaos.chaosmod.jobs.gui.fisherman;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.jobs.events.fisherman.ClientFishingData;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.ui.components.GuiUtils;

@SideOnly(Side.CLIENT)
public class GuiFishingResult extends GuiScreen {
	private final int light_dark = 0xff24292f;

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);

		drawRect(0, 0, width, height, light_dark);
		
		ItemStack stack = ClientFishingData.lastLoot;

		String str = String.format("You got [%s] x%d", stack.getDisplayName(), stack.getCount());
		drawCenteredString(fontRenderer, str, width / 2, 20, 0xff00ff00);

		int scale = 8;
		int x = width / 2 - 8 * scale;
		int y = 50;
		if (stack != null) {
			GlStateManager.pushMatrix();
			GlStateManager.translate(x, y, 0);
			GlStateManager.scale(scale, scale, 1.0F);

			itemRender.renderItemAndEffectIntoGUI(stack, 0, 0);

			GlStateManager.popMatrix();
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
		
		switch (keyCode) {
			case Keyboard.KEY_SPACE:
				GuiUtils.closeCurrentScreen(mc);
				break;
			default:break;
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}