package net.chaos.chaosmod.jobs;

import net.chaos.chaosmod.Main;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SpriteComponent extends UIComponent {
	public ResourceLocation rl;
	public int u;
	public int v;
	public UIComponent parent;

	public SpriteComponent(GuiScreen screen, UIComponent parent, int x, int y, int u, int v, int width, int height, ResourceLocation rl) {
		super(screen, parent.x + x, parent.y + y, width, height);
		this.screen = screen;
		this.parent = parent;
		this.rl = rl;
		this.u = u;
		this.v = v;
	}
	
	@SideOnly(Side.CLIENT)
	public void render() {
		screen.mc.getTextureManager().bindTexture(rl);
		screen.drawTexturedModalRect(this.x, this.y, this.u, this.v, this.width, this.height);
	}
	
	public void update() {
		
	}

}
