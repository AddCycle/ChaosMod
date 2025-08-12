package net.chaos.chaosmod.jobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import util.ui.components.JobsButtonBase;

@SideOnly(Side.CLIENT)
public class GuiScreenJobs extends GuiScreen {
	public boolean DEBUG = false;
	public static final ResourceLocation WIDGETS = new ResourceLocation(Reference.MODID, "textures/jobs/widgets.png");
	public List<JobComponent> components = new ArrayList<>();
	public int dark = 0xff24292f;
	public int light_dark = 0xff24292f;

    private int scrollX;
    private int scrollY;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    private int scrollMouseX;
    private int scrollMouseY;

    private boolean isScrolling;
	
	public GuiScreenJobs(@Nullable GuiJobs jobs) { // Jobs
		JobsList.init();
	}

	@Override
	public void initGui() {
		JobsManager.JOBS_REGISTRY.forEach(job -> {
			int componentWidth = 80;
			int componentHeight = 160;
			// int centerX = this.width / 2 - componentWidth / 2;
			int centerY = this.height / 2 - componentHeight / 2;
			int rightOffset = 20;
			int offsetX = job.index * (componentWidth + 20) + rightOffset;
			JobComponent parent = new JobComponent(this, job, offsetX, centerY, componentWidth, componentHeight);
			components.add(parent);
			int componentSize = 32;
			int x = parent.width / 2 - componentSize / 2;
			int y = parent.height / 2 - componentSize / 2;
			buttonList.add(new JobsButtonBase(job.index, parent, x, y, 32, 32, ""));
		});
		super.initGui();
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

    public void scroll(int x, int y)
    {
        if (this.maxX - this.minX > 234)
        {
            this.scrollX = MathHelper.clamp(this.scrollX + x, -(this.maxX - 234), 0);
        }

        if (this.maxY - this.minY > 113)
        {
            this.scrollY = MathHelper.clamp(this.scrollY + y, -(this.maxY - 113), 0);
        }
    }

	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int i = (this.width - 252) / 2;
        int j = (this.height - 140) / 2;

        if (Mouse.isButtonDown(0))
        {
            if (!this.isScrolling)
            {
                this.isScrolling = true;
            }
            /*else if (this.selectedTab != null)
            {
                this.selectedTab.scroll(mouseX - this.scrollMouseX, mouseY - this.scrollMouseY);
            }*/
            this.scroll(mouseX - this.scrollMouseX, mouseY - this.scrollMouseY);

            this.scrollMouseX = mouseX;
            this.scrollMouseY = mouseY;
        }
        else
        {
            this.isScrolling = false;
        }

		drawDefaultBackground();
		// drawForeground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void updateScreen() {
		drawForeground();
		super.updateScreen();
	}
	
	public void drawForeground() {
		this.drawCenteredString(fontRenderer, "Jobs les amis j'en ai marre de cette histoire", this.width / 2, fontRenderer.FONT_HEIGHT, 0xffffff); // title
		components.forEach(jobComponent -> {
			jobComponent.render();
		});
	}
	
	@Override
	public void drawDefaultBackground() {
		this.drawGradientRect(0, 0, this.width, this.height, light_dark, dark);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == Keyboard.KEY_NUMPADENTER) {
			DEBUG = !DEBUG;
		}
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public void onGuiClosed() {
		components.clear();
		buttonList.clear();
		super.onGuiClosed();
	}
}
