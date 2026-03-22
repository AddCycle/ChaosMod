package util.ui.components;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiUtils {

	public static GuiLabel guiLabel(FontRenderer fontRenderer, int id, int x, int y, int width, int height, int textColor) {
		return new GuiLabel(fontRenderer, id, x, y, width, height, textColor);
	}
	
	public static void closeCurrentScreen(Minecraft mc) {
		mc.displayGuiScreen((GuiScreen)null);

		if (mc.currentScreen == null)
		{
			mc.setIngameFocus();
		}
	}
}