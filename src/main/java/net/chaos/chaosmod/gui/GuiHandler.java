package net.chaos.chaosmod.gui;

import net.chaos.chaosmod.client.gui.inventory.BackpackGui;
import net.chaos.chaosmod.client.gui.inventory.ForgeInterfaceGui;
import net.chaos.chaosmod.client.gui.inventory.OxoniumFurnaceGui;
import net.chaos.chaosmod.inventory.BackpackContainer;
import net.chaos.chaosmod.inventory.ForgeInterfaceContainer;
import net.chaos.chaosmod.inventory.OxoniumFurnaceContainer;
import net.chaos.chaosmod.items.special.PlayerInventoryBaseItem;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import util.Reference;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		switch (ID) {
		case 0:
			return null; // new ContainerNull(player.inventory, player);
		case 1:
			return new OxoniumFurnaceContainer(player.inventory, (TileEntityOxoniumFurnace) te); // new ContainerNull(player.inventory, player);
		case 2: 
			return null;
		case 3:
			return new ForgeInterfaceContainer(player.inventory, (TileEntityForge) te);
		case 4:
			ItemStack held = player.getHeldItemMainhand();
		    if (held.getItem() instanceof PlayerInventoryBaseItem) {
		        return new BackpackContainer(player.inventory, held);
		    }
		    return null;
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
		case 3:
			return new ForgeInterfaceGui(player.inventory, (TileEntityForge) te);
		case 4:
			return new BackpackGui(new BackpackContainer(player.inventory, player.getHeldItemMainhand()));
			
			// return new BackpackGui(player.inventory, new InventoryBackpack(player.inventory, 54));
		default:
			throw new IllegalArgumentException("Unexpected value: " + ID);
		}
	}

}
