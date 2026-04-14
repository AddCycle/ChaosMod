package net.chaos.chaosmod.jobs.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.chaos.chaosmod.common.capabilities.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.jobs.task.JobTask;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiScreenTask extends GuiScreen {
	private GuiScreenJob parent;
	private final JobTask task;
    private PlayerJobs jobs;

	public GuiScreenTask(GuiScreenJob parent, JobTask task) {
		this.parent = parent;
		this.task = task;
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

    	super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawSplitString(String text, int x, int y, int width, int color) {
        this.fontRenderer.drawSplitString(text, x, y, width, color);
    }
}