package net.chaos.chaosmod.jobs.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.Job;
import net.chaos.chaosmod.jobs.JobProgress;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.jobs.gui.task.TaskListEntry;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenJob extends GuiScreen {
	private final GuiScreen parent;
    private final Job job;
    private PlayerJobs jobs;
    private List<TaskListEntry> entries = new ArrayList<>();
    private int startX, taskSpacing = 10;

    public GuiScreenJob(GuiScreen parent, Job job) {
        this.parent = parent;
        this.job = job;
    }

    @Override
    public void initGui() {
    	if (jobs == null && mc.player != null) {
    		this.jobs = mc.player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
    	}

        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height - 30, "Back"));

        startX = this.width / 2 - 150;
        initTaskEntries();
    }
    
    private void initTaskEntries() {
    	entries.clear();
    	int y = 80;
    	for (JobTask task : job.tasks) {
    	    TaskListEntry entry = new TaskListEntry(job, task, jobs);
    	    entry.setPosition(startX, y, 300);
    	    entries.add(entry);
    	    y += entry.getHeight() + taskSpacing;
    	}
	}

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(parent);
        }
    }
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE)
        {
        	if (parent == null) {
        		mc.displayGuiScreen(null);
        		mc.setIngameFocus();
        	}
        	mc.displayGuiScreen(parent);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();

        // Job name
        drawCenteredString(this.fontRenderer, job.name, this.width / 2, 20, 0xFFFFFF);

        // Description
        drawSplitString(job.description, startX, 50, 200, 0xAAAAAA);
        
        // Tasks
        int y = 80;
        int taskIndex = 1;
        for (TaskListEntry entry : entries) {
        	entry.draw(fontRenderer, taskIndex++);
            y += this.fontRenderer.FONT_HEIGHT + taskSpacing;
        }

        if (jobs != null) {
            JobProgress progress = jobs.getProgress(job.id);
            int level = progress != null ? progress.getLevel() : 0;
            int exp = progress != null ? progress.getExp() : 0;
            int totalExp = progress != null ? progress.getExpToNextLevel(level) : 0;
            int lineWidth = 40;
            int off = (this.width - lineWidth) / 2;
            drawHorizontalLine(off, this.width - off, y + 24, -0x5f6f6f70);

            float percentage = (float) level / (float)job.maxLevel;
            drawProgressBar(startX, y, 300, 20, percentage, 0xff5f0000, 0xff9f0000);

            // FIXME : put everything below inside progressbar and make the exp act as the progressbar fill when it won't be a fixed amount for each task leading to almost not see the progressbar filled
            drawCenteredString(fontRenderer, "Level: " + level + "/" + job.maxLevel, this.width / 2, y + 6, 0xffffff);
            drawCenteredString(fontRenderer, "Exp: " + exp + "/" + totalExp, this.width / 2, y + 30, 0xffffff);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    	super.mouseClicked(mouseX, mouseY, mouseButton);

    	for (TaskListEntry entry : entries) {
    	    if (entry.isMouseOver(mouseX, mouseY)) {
    	        mc.displayGuiScreen(new GuiScreenTask(this, entry.getTask()));
    	    }
    	}
    }

    /**
     * Draws jobs level progress bar until next level
     * @param x
     * @param y
     * @param width
     * @param height
     * @param percentage 		between 0f and 1f
     * @param color1 			startColor (ARGB)
     * @param color2 			endColor (ARGB)
     */
    private void drawProgressBar(int x, int y, int width, int height, float percentage, int color1, int color2) {
    	drawRect(x, y, x + width, y + height, 0xff000000);
    	drawGradientRect(x, y, x + (int) (percentage * width), y + height, color1, color2);
    }

    private void drawSplitString(String text, int x, int y, int width, int color) {
        this.fontRenderer.drawSplitString(text, x, y, width, color);
    }
}