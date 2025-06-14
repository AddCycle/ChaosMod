package net.chaos.chaosmod.gui;

import net.chaos.chaosmod.client.gui.inventory.BackpackGui;
import net.chaos.chaosmod.client.gui.inventory.ForgeInterfaceGui;
import net.chaos.chaosmod.client.gui.inventory.GuiInventoryExtended;
import net.chaos.chaosmod.client.gui.inventory.OxoniumFurnaceGui;
import net.chaos.chaosmod.client.inventory.ContainerAccessory;
import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.inventory.BackpackContainer;
import net.chaos.chaosmod.inventory.ContainerChaosSage;
import net.chaos.chaosmod.inventory.ForgeInterfaceContainer;
import net.chaos.chaosmod.inventory.OxoniumFurnaceContainer;
import net.chaos.chaosmod.inventory.TrophyContainerBase;
import net.chaos.chaosmod.items.special.PlayerInventoryBaseItem;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.chaos.chaosmod.tileentity.TileEntityTrophyBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
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
		case 5:
			return new Container() {
				@Override
				public boolean canInteractWith(EntityPlayer playerIn) {
					return true;
				}
			};
		case 6:
			return new ContainerAccessory(player.inventory, world.isRemote, player);
		case 7:
			return new TrophyContainerBase(player.inventory, (TileEntityTrophyBase) te);
		default:
			throw new IllegalArgumentException("Unexpected value: " + ID);
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		Container container = EntityContains(player, world, x);
		
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
		case 5:
			System.out.println("Server GuiHandler received ID: " + ID + " x: " + x);
			return new GuideGui(0);
		case 6:
			return new GuiInventoryExtended(player);
		case 7:
			return new TrophyGui(player.inventory, (TileEntityTrophyBase) te);
			
			// return new BackpackGui(player.inventory, new InventoryBackpack(player.inventory, 54));
		default:
			throw new IllegalArgumentException("Unexpected value: " + ID);
		}
	}

	private Container EntityContains(EntityPlayer player, World world, int entityId) {
		Entity entity = world.getEntityByID(entityId);
		if (entity instanceof EntityChaosSage && !entity.isDead)
        {
			System.out.println("CONTAINER(entityID) : " + entityId);
			return new ContainerChaosSage();
        }
		return null;
	}

}
