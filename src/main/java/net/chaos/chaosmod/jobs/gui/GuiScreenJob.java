package net.chaos.chaosmod.jobs.gui;

import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.Job;
import net.chaos.chaosmod.jobs.JobProgress;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenJob extends GuiScreen {
	private final GuiScreen parent;
    private final Job job;
    private PlayerJobs jobs;

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
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 0) {
            mc.displayGuiScreen(parent);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        
        // Job name
        drawCenteredString(this.fontRenderer, job.name, this.width / 2, 20, 0xFFFFFF);

        // Description
        drawSplitString(job.description, this.width / 2 - 100, 50, 200, 0xAAAAAA);
        
        // Tasks
        int y = 90;
        int index = 1;
        for (JobTask task : job.tasks) {
        	int progress = jobs.getProgress(job.id).getTaskProgress(task.id);
        	if (progress >= task.goal) {
        		drawString(this.fontRenderer, String.format("%d - %s :" + TextFormatting.GREEN + "[COMPLETED]" + TextFormatting.RESET + " exp = %d", index++, task.name, task.rewardExp), this.width / 2 - 100, y, 0xFFFFFF);
        	} else {
        		drawString(this.fontRenderer, String.format("%d - %s : (%d/%d) exp = %d", index++, task.name, progress, task.goal, task.rewardExp), this.width / 2 - 100, y, 0xFFFFFF);
        	}
            y += this.fontRenderer.FONT_HEIGHT + 4;
        }

        if (jobs != null) {
            JobProgress progress = jobs.getProgress(job.id);
            int level = progress != null ? progress.getLevel() : 0;
            int exp = progress != null ? progress.getExp() : 0;
            int totalExp = progress != null ? progress.getExpToNextLevel(level) : 0;
            drawCenteredString(fontRenderer, "Level: " + level, this.width / 2, y, 0xffffff);
            drawCenteredString(fontRenderer, "Exp: " + exp + "/" + totalExp, this.width / 2, y + 20, 0xffffff);
        } else {
            drawCenteredString(fontRenderer, "Loading...", this.width / 2, 50, 0xFF0000);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSplitString(String text, int x, int y, int width, int color) {
        this.fontRenderer.drawSplitString(text, x, y, width, color);
    }
}