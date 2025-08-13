package net.chaos.chaosmod.jobs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.chaos.chaosmod.Main;
import net.minecraft.client.gui.GuiButton;
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
	public static final ResourceLocation FINAL_VER = new ResourceLocation(Reference.MODID, "textures/jobs/final_ver.png");
	public List<JobComponent> components = new ArrayList<>();
	public int dark = 0xff24292f;
	public int light_dark = 0xff24292f;

    public int scrollX;
    public int minX = Integer.MAX_VALUE;
    public int maxX = Integer.MIN_VALUE;

    public int scrollMouseX;

    public boolean isScrolling;

    public int scrollStartMouseX = -1;
    public boolean isDragging = false;
	
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
			GuiButton btn = new JobsButtonBase(job.index, this, parent, x, y, 32, 32, "");
			parent.addButton(btn);
			buttonList.add(btn);
		});
		super.initGui();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	public void scroll(int deltaX) {
	    if (minX == Integer.MAX_VALUE || maxX == Integer.MIN_VALUE) {
	        // calculate minX and maxX
	        minX = Integer.MAX_VALUE;
	        maxX = Integer.MIN_VALUE;
	        components.forEach(c -> {
	            if (c.x < minX) minX = c.x;
	            if (c.x + c.width > maxX) maxX = c.x + c.width;
	        });
	    }
	    int visibleWidth = this.width - 40;
	    int contentWidth = maxX - minX;

	    this.scrollX = MathHelper.clamp(this.scrollX + deltaX, 0, Math.max(0, contentWidth - visibleWidth));
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        int i = (this.width - 252) / 2;
        int j = (this.height - 140) / 2;

        if (Mouse.isButtonDown(0)) {
            if (scrollStartMouseX == -1) {
                // First frame mouse is down — remember start position
                scrollStartMouseX = mouseX;
                isDragging = false;
            } else {
                int deltaX = mouseX - scrollStartMouseX;

                // Only start dragging if moved more than threshold (e.g. 2 pixels)
                if (!isDragging && Math.abs(deltaX) > 2) {
                    isDragging = true;
                    isScrolling = true;
                }

                if (isDragging) {
                    this.scroll(deltaX);

                    scrollStartMouseX = mouseX; // update for next frame
                }
            }
        } else {
            isScrolling = false;
            isDragging = false;
            scrollStartMouseX = -1;
        }

		this.drawDefaultBackground(scrollX);
		drawForeground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
	}
	
	public void drawForeground() {
		this.drawCenteredString(fontRenderer, "Jobs les amis j'en ai marre de cette histoire", this.width / 2, fontRenderer.FONT_HEIGHT, 0xffffff); // title
		components.forEach(jobComponent -> {
			jobComponent.render();
		});
	}
	
	// @Override
	public void drawDefaultBackground(int scrollX) {
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
