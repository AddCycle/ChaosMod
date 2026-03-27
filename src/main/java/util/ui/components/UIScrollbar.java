package util.ui.components;

import net.chaos.chaosmod.jobs.ui.UIComponent;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIScrollbar extends UIComponent {

	public UIScrollbar(GuiScreen screen, int x, int y, int width, int height) {
		super(screen, x, y, width, height);
	}

	@Override
	public void render() {
		Gui.drawRect(x, y, x + width, y + height, 0xffffffff);
	}

	@Override
	public void update() {

	}
}