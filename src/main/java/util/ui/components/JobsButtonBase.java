package util.ui.components;

import net.chaos.chaosmod.jobs.GuiScreenJobs;
import net.chaos.chaosmod.jobs.UIComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import util.Reference;

public class JobsButtonBase extends GuiButton {
    protected static final ResourceLocation BUTTONS = new ResourceLocation(Reference.MODID, "textures/jobs/widgets.png");
    public UIComponent parent;
    public GuiScreenJobs screen;

	public JobsButtonBase(int buttonId, GuiScreenJobs screen, UIComponent parent, int x, int y, int widthIn, int heightIn, String buttonText) {
		super(buttonId, parent.x + x, parent.y + y, widthIn, heightIn, buttonText);
		this.screen = screen;
		this.parent = parent;
	}

	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.drawCustomButton(mc, mouseX, mouseY, partialTicks);
	}

	public void drawCustomButton(Minecraft mc, int mouseX, int mouseY, float partialTicks)
	{
		if (this.visible)
		{
			// FontRenderer fontrenderer = mc.fontRenderer;
			int scrollX = this.x - this.screen.scrollX;
			mc.getTextureManager().bindTexture(BUTTONS);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.hovered = mouseX >= scrollX && mouseY >= this.y && mouseX < scrollX + this.width && mouseY < this.y + this.height;
			int i = this.getHoverState(this.hovered);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			this.drawTexturedModalRect(scrollX, this.y, this.id * 32, 160 + i * 32, this.width, this.height);
			this.mouseDragged(mc, mouseX, mouseY);
		}
	}
}
