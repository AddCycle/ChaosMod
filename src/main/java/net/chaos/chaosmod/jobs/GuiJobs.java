package net.chaos.chaosmod.jobs;

import net.minecraft.client.gui.Gui;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiJobs extends Gui {
	private GuiScreenJobs screen;
    private int scrollX;
    private int scrollY;
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;
    
    public GuiJobs() {
    	
    }

}
