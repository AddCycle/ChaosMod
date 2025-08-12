package net.chaos.chaosmod.jobs;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class UIComponent {
	public int x;
	public int y;
	public int width;
	public int height;
	public GuiScreen screen;
	
	public UIComponent(GuiScreen screen, int x, int y, int width, int height) {
		this.screen = screen;
		this.width = width;
		this.height = height;
		this.setPosition(x, y);
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@SideOnly(Side.CLIENT)
	public abstract void render();
	
	public abstract void update();
}
