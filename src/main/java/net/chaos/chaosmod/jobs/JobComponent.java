package net.chaos.chaosmod.jobs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;

public class JobComponent extends UIComponent {
	public Job job;
	public int icon;
	public List<JobTask> tasks = new ArrayList<>();
	public List<UIComponent> children = new ArrayList<>();
	public List<GuiButton> buttons = new ArrayList<>();
	public static final ResourceLocation WIDGETS = new ResourceLocation(Reference.MODID, "textures/jobs/widgets.png");

	public JobComponent(GuiScreen screen, Job job, int x, int y, int width, int height) {
		super(screen,x,y,width,height);
		this.job = job;
		this.icon = job.index;
		// int componentSize = 32;
		// int centerX = this.width / 2 - componentSize / 2;
		// int centerY = this.height / 2 - componentSize / 2;
		// this.children.add(new SpriteComponent(screen, this, centerX, centerY, 0 + (this.icon * componentSize), 192, componentSize, componentSize, WIDGETS));
	}
	
	public void addButton(GuiButton button) {
		this.buttons.add(button);
	}

	public void removeButton(GuiButton button) {
		this.buttons.remove(button);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void render() {
		this.renderJob(this);
		this.children.forEach(child -> child.render());
	}

	@Override
	public void update() {
		this.children.forEach(child -> child.update());
	}

	public void renderJob(JobComponent jobComponent) {
		int calculatedX = x - ((GuiScreenJobs) screen).scrollX;
		drawJobComponent(calculatedX, y);
		drawJobInfos(calculatedX, y);
		// drawButtons();
	}

	public void drawJobComponent(int x, int y) {
		screen.mc.getTextureManager().bindTexture(WIDGETS);
		screen.drawTexturedModalRect(x, y, 0, 0, this.width, this.height);
	}

	public void drawJobInfos(int x, int y) {
		FontRenderer fr = screen.mc.fontRenderer;
		Job job = this.job;
		screen.drawCenteredString(fr, job.name, x + this.width / 2, y + fr.FONT_HEIGHT, 0xffffff);

		String text = job.description;
		if (text.isEmpty()) return;

		int text_width = fr.getStringWidth(text);

		int componentWidth = this.width;
		String[] words = text.split(" ");
		StringBuilder line = new StringBuilder();
		int lineIndex = 0;
		int offsetX = 5;
		int offsetY = 10;

		for (String word : words) {
		    String testLine = (line.length() == 0) ? word : line + " " + word;

		    if (fr.getStringWidth(testLine) < componentWidth - offsetX) {
		        line = new StringBuilder(testLine);
		    } else {
		        this.drawString(fr, line.toString(), x + offsetX, y + (fr.FONT_HEIGHT + 2) * lineIndex + offsetY, 0xFFFFFF);
		        line = new StringBuilder(word);
		        lineIndex++;
		    }
		}

		// Draw last remaining line
		if (line.length() > 0) {
		        this.drawString(fr, line.toString(), x + offsetX, y + (fr.FONT_HEIGHT + 2) * lineIndex + offsetY, 0xFFFFFF);
		}
	}
	
	private void drawString(FontRenderer fr, String text, int x, int y, int color) {
		fr.drawStringWithShadow(text, (float)(x), (float)y, color);
	}
}