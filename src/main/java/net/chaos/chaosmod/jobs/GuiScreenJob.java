package net.chaos.chaosmod.jobs;

import net.chaos.chaosmod.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenJob extends GuiScreen {
	private final GuiScreen parent;
    private final Job job; // The selected job

    public GuiScreenJob(GuiScreen parent, Job job) {
        this.parent = parent;
        this.job = job;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        // Back button
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
            drawString(this.fontRenderer, String.format("%d - %s : (%d/%d)", index++, task.name, task.progress, task.goal), this.width / 2 - 100, y, 0xFFFFFF);
            y += this.fontRenderer.FONT_HEIGHT + 4;
        }

        // Level
        PlayerJobs jobs = mc.player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
        if (jobs != null) {
            JobProgress progress = jobs.getProgress(job.id);
            int level = progress != null ? progress.getLevel() : 0;
            int exp = progress != null ? progress.getExp() : 0;
            drawCenteredString(fontRenderer, "Level: " + level, this.width / 2, y, 0xffffff);
            drawCenteredString(fontRenderer, "Exp: " + exp, this.width / 2, y + 20, 0xffffff);
            // ... draw stuff
            // drawCenteredString(fontRenderer, String.format("Level: %d", level), this.width / 2, y + this.fontRenderer.FONT_HEIGHT + 4, 0xffffff);
        } else {
            drawCenteredString(fontRenderer, "Loading...", this.width / 2, 50, 0xFF0000);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSplitString(String text, int x, int y, int width, int color) {
        this.fontRenderer.drawSplitString(text, x, y, width, color);
    }
}
