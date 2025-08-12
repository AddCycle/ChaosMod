package util.ui.components;

import net.chaos.chaosmod.jobs.UIComponent;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TaskComponent extends UIComponent {

	public TaskComponent(GuiScreen screen, int x, int y, int width, int height) {
		super(screen, x, y, width, height);
	}

	@Override
	public void update() {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render() {
		
	}

}
