package net.chaos.chaosmod.jobs.gui;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.jobs.data.ClientJobsCache;
import net.chaos.chaosmod.jobs.data.ClientSharedTaskCache;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketRequestAllPlayersJobs;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * TODO : display each player advancement in each task like to see overall players progress to kinda motive others to do tasks too [DONE -> needs polish]
 */
@SideOnly(Side.CLIENT)
public class GuiScreenTask extends GuiScreen {
	private GuiScreenJob parent;
	private final JobTask task;
    private PlayerJobs jobs;
    private String jobId;

	public GuiScreenTask(GuiScreenJob parent, JobTask task, String jobId) {
		this.parent = parent;
		this.task = task;
		this.jobId = jobId;
	}

	@Override
	public void initGui() {
    	if (jobs == null && mc.player != null) {
    		this.jobs = mc.player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null);
    	}

    	// Request fresh data from server when GUI opens
        PacketManager.network.sendToServer(new PacketRequestAllPlayersJobs());

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

        int startX = this.width / 2 - 150;

    	// Task name
        drawCenteredString(this.fontRenderer, task.name, this.width / 2, 20, 0xFFFFFF);

        // taskId
        drawCenteredString(this.fontRenderer, "id: " + task.id, this.width / 2, 30, 0x888888);

        // RewardExp
        drawCenteredString(this.fontRenderer, "rewardExp: " + task.rewardExp, this.width / 2, 60, 0xCCCCCC);

        if (task.shared) {
        	// isShared ?
        	drawCenteredString(this.fontRenderer, "(shared)", this.width / 2, 40, 0xCCCCCC);
        }

        // Task Description
        drawSplitString(task.description, startX, 100, 200, 0xDDDDDD);
        
        // TODO : Make a new gui for this maybe...
        // FIXME : support offline/historical data even if the player isn't online so everyone are still competition
        int yOffset = 160;
        if (task.shared) {
            int total = ClientSharedTaskCache.getTaskProgress(jobId, task.id);
            drawCenteredString(this.fontRenderer, "Total: " + total + "/" + task.goal, this.width / 2, yOffset, 0xFFAA00);
            yOffset += 12;

            Map<UUID, Integer> contributions = ClientSharedTaskCache.getContributions(jobId, task.id);
            for (Map.Entry<UUID, Integer> entry : contributions.entrySet()) {
                String name = ClientJobsCache.INSTANCE.getName(entry.getKey()); // reuse name cache
                this.fontRenderer.drawString(name + ": " + entry.getValue(), startX, yOffset, 0xAAAAAA);
                yOffset += 10;
            }
        } else {
            for (Map.Entry<UUID, PlayerJobs> entry : ClientJobsCache.INSTANCE.getAll().entrySet()) {
                String name = ClientJobsCache.INSTANCE.getName(entry.getKey());
                int progress = entry.getValue().getProgress(jobId).getTaskProgress(task.id);
                this.fontRenderer.drawString(name + ": " + progress + "/" + task.goal, startX, yOffset, 0xAAAAAA);
                yOffset += 10;
            }
        }
//        Map<UUID, PlayerJobs> allJobs = ClientJobsCache.INSTANCE.getAll();
//        for (Map.Entry<UUID, PlayerJobs> entry : allJobs.entrySet()) {
//            String playerName = ClientJobsCache.INSTANCE.getName(entry.getKey());
//            int progress = entry.getValue().getProgress(jobId).getTaskProgress(task.id);
//            String line = playerName + ": " + progress;
//            this.fontRenderer.drawString(line, startX, yOffset, 0xAAAAAA);
//            yOffset += 10;
//        }

    	super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSplitString(String text, int x, int y, int width, int color) {
        this.fontRenderer.drawSplitString(text, x, y, width, color);
    }
}