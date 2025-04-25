package net.chaos.chaosmod.gui;

import net.chaos.chaosmod.client.gui.inventory.OxoniumFurnaceGui;
import net.chaos.chaosmod.inventory.OxoniumFurnaceContainer;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
		case 0:
			return null; // new ContainerNull(player.inventory, player);
		case 1:
			return new OxoniumFurnaceContainer(player.inventory, (TileEntityOxoniumFurnace) te); // new ContainerNull(player.inventory, player);
		case 2: return null;
			// return new BackpackContainer(player.inventory, new InventoryBackpack(player.inventory, 54)); // maybe bound to a player Inventory and itemhandler
		default:
			throw new IllegalArgumentException("Unexpected value: " + ID);
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
		case 0:
			return new GuideGui(0);
		case 1:
			return new OxoniumFurnaceGui(player.inventory, (TileEntityOxoniumFurnace) te);
		case 2:
			return new GuiFinalCredits();
			// return new BackpackGui(player.inventory, new InventoryBackpack(player.inventory, 54));
		default:
			throw new IllegalArgumentException("Unexpected value: " + ID);
		}
	}

}
