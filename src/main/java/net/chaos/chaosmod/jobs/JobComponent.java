package net.chaos.chaosmod.jobs;

import java.util.ArrayList;
import java.util.List;

import net.chaos.chaosmod.Main;
import net.minecraft.client.gui.FontRenderer;
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
	public static final ResourceLocation WIDGETS = new ResourceLocation(Reference.MODID, "textures/jobs/widgets.png");

	public JobComponent(GuiScreen screen, Job job, int x, int y, int width, int height) {
		super(screen,x,y,width,height);
		this.job = job;
		this.icon = job.index;
		int componentSize = 32;
		int centerX = this.width / 2 - componentSize / 2;
		int centerY = this.height / 2 - componentSize / 2;
		// this.children.add(new SpriteComponent(screen, this, centerX, centerY, 0 + (this.icon * componentSize), 192, componentSize, componentSize, WIDGETS));
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
		drawJobComponent(x, y);
		drawJobInfos(this);
	}

	public void drawJobComponent(int x, int y) {
		screen.mc.getTextureManager().bindTexture(WIDGETS);
		screen.drawTexturedModalRect(x, y, 0, 0, this.width, this.height);
	}

	@SideOnly(Side.CLIENT)
	public void drawJobInfos(JobComponent jobComponent) {
		FontRenderer fr = screen.mc.fontRenderer;
		Job job = jobComponent.job;
		screen.drawCenteredString(fr, job.name, this.x + this.width / 2, this.y + fr.FONT_HEIGHT, 0xffffff);
	}
	
}