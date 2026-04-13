package net.chaos.chaosmod.jobs.gui.task;

import net.chaos.chaosmod.jobs.Job;
import net.chaos.chaosmod.jobs.JobProgress;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.jobs.data.ClientSharedTaskCache;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TaskListEntry {
	private Job job;
	private JobTask task;
    private PlayerJobs jobs;

    private int x, y, width, height;

	public TaskListEntry(Job job, JobTask task, PlayerJobs jobs) {
		this.job = job;
		this.task = task;
		this.jobs = jobs;
	}

	public void setPosition(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = 10;
    }

	public void setPosition(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public int getHeight() {
        return height;
    }

    /** Returns true if the point is inside this entry's bounds */
    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width
            && mouseY >= y && mouseY <= y + height;
    }

    public void draw(FontRenderer fontRenderer, int taskIndex) {
        int progress = resolveProgress();
        Gui.drawRect(x - 5, y - 5, x + width + 3, y + height + 3, -0x5f6f6f70);

        boolean complete = progress >= task.goal;
        String suffix = task.shared ? " (shared)" : "";

        if (complete) {
            fontRenderer.drawString(
                String.format("%d - %s %s[COMPLETED]%s %d EXP%s",
                    taskIndex, task.name,
                    TextFormatting.GREEN, TextFormatting.RESET,
                    task.rewardExp, suffix),
                x, y, 0xFFFFFF, false);
        } else {
            fontRenderer.drawString(
                String.format("%d - %s : (%d/%d) | %d EXP | %s%s",
                    taskIndex, task.name,
                    progress, task.goal,
                    task.rewardExp, task.type.name, suffix),
                x, y, 0xFFFFFF, false);
        }
    }

    private int resolveProgress() {
        if (task.shared) {
            return ClientSharedTaskCache.getProgress(job.id, task.id);
        }
        if (jobs == null) return 0;
        JobProgress progress = jobs.getProgress(job.id);
        if (progress == null) return 0; // <-- fixes the NPE
        return progress.getTaskProgress(task.id);
    }
    
    public JobTask getTask() { return task; }
}
