package net.chaos.chaosmod.network;

import net.chaos.chaosmod.client.gui.inventory.OxoniumFurnaceGui;
import net.chaos.chaosmod.gui.GuideGui;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class ALZ {
	public static void alz0() {
		Minecraft.getMinecraft().displayGuiScreen(new GuideGui(0));
	}
	
	public static void alz1() {
		Minecraft mc = Minecraft.getMinecraft();
		TileEntity tile = mc.world.getTileEntity(mc.player.getPosition().add(1, 0, 0));
		if (tile instanceof TileEntityOxoniumFurnace) {
			mc.displayGuiScreen(new OxoniumFurnaceGui(mc.player.inventory, (TileEntityOxoniumFurnace) tile));
		}
	}

}
