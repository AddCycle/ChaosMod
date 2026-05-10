package net.chaos.chaosmod.gui;

import javax.annotation.Nullable;

import net.chaos.chaosmod.client.gui.inventory.BackpackGui;
import net.chaos.chaosmod.client.gui.inventory.ForgeInterfaceGui;
import net.chaos.chaosmod.client.gui.inventory.GuiInventoryExtended;
import net.chaos.chaosmod.client.gui.inventory.OxoniumFurnaceGui;
import net.chaos.chaosmod.client.inventory.ContainerAccessory;
import net.chaos.chaosmod.entity.EntityChaosSage;
import net.chaos.chaosmod.entity.animal.container.ContainerBear;
import net.chaos.chaosmod.entity.animal.gui.GuiBear;
import net.chaos.chaosmod.inventory.ATMContainer;
import net.chaos.chaosmod.inventory.BackpackContainer;
import net.chaos.chaosmod.inventory.ContainerUpgradingStation;
import net.chaos.chaosmod.inventory.ForgeInterfaceContainer;
import net.chaos.chaosmod.inventory.OxoniumFurnaceContainer;
import net.chaos.chaosmod.inventory.TrophyContainerBase;
import net.chaos.chaosmod.items.ItemBiomeCompass;
import net.chaos.chaosmod.items.special.PlayerInventoryBaseItem;
import net.chaos.chaosmod.jobs.gui.fisherman.GuiFishingMinigame;
import net.chaos.chaosmod.tileentity.TileEntityATM;
import net.chaos.chaosmod.tileentity.TileEntityForge;
import net.chaos.chaosmod.tileentity.TileEntityJigsaw;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.chaos.chaosmod.tileentity.TileEntityTrophyBase;
import net.chaos.chaosmod.tileentity.TileEntityUpgradingStation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import util.Reference;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		Entity entity = world.getEntityByID(x);

		InventoryPlayer inventory = player.inventory;

		switch (ID) {
		case 0:
			return null;
		case Reference.GUI_FURNACE_ID:
			return new OxoniumFurnaceContainer(inventory, (TileEntityOxoniumFurnace) te);
		case Reference.GUI_CREDITS_ID:
			return emptyContainer();
		case 3:
			return new ForgeInterfaceContainer(inventory, (TileEntityForge) te);
		case 4:
			ItemStack held = player.getHeldItemMainhand();
		    if (held.getItem() instanceof PlayerInventoryBaseItem) {
		        return new BackpackContainer(inventory, held);
		    }
		    return null;
		case 5:
			return emptyContainer();
		case Reference.GUI_ACCESSORY_ID:
			return new ContainerAccessory(inventory, world.isRemote, player);
		case 7:
			return new TrophyContainerBase(inventory, (TileEntityTrophyBase) te);
		case Reference.GUI_ATM_ID:
			return new ATMContainer(inventory, (TileEntityATM) te, player);
		case Reference.GUI_MARKET_ID:
			// return new MarketContainer(inventory, );
			// FIXME : add a container with the slots
			return emptyContainer();
		case Reference.GUI_FISHINGGAME_ID:
			return emptyContainer();
		case Reference.GUI_BEAR_ID:
			return new ContainerBear(player, (EntityLiving) entity);
		case Reference.GUI_JIGSAW_ID:
			return emptyContainer();
		case Reference.GUI_BIOME_COMPASS:
			return emptyContainer();
		case Reference.GUI_UPGRADING_STATION:
			return new ContainerUpgradingStation(inventory, (TileEntityUpgradingStation) te, world, new BlockPos(x, y, z));
		default:
			throw new IllegalArgumentException("Unexpected value: " + ID);
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		Entity entity = world.getEntityByID(x);
		
		switch (ID) {
		case 0:
			throw new IllegalArgumentException("Unexpected value: " + ID);
		case Reference.GUI_FURNACE_ID:
			return new OxoniumFurnaceGui(player.inventory, (TileEntityOxoniumFurnace) te);
		case Reference.GUI_CREDITS_ID:
			return new GuiFinalCredits();
		case Reference.GUI_FORGE_ID:
			return new ForgeInterfaceGui(player.inventory, (TileEntityForge) te);
		case Reference.GUI_BACKPACK_ID:
			return new BackpackGui(new BackpackContainer(player.inventory, player.getHeldItemMainhand()));
		case 5:
			throw new IllegalArgumentException("Unexpected value: " + ID);
		case Reference.GUI_ACCESSORY_ID:
			return new GuiInventoryExtended(player);
		case Reference.GUI_TROPHY:
			return new TrophyGui(player.inventory, (TileEntityTrophyBase) te);
		case Reference.GUI_ATM_ID:
			return new GuiATM(player.inventory, (TileEntityATM) te, player);
		case Reference.GUI_MARKET_ID:
			return new GuiMarket();
		case Reference.GUI_FISHINGGAME_ID:
			return new GuiFishingMinigame();
		case Reference.GUI_BEAR_ID:
			return new GuiBear(player, (EntityLiving) entity);
		case Reference.GUI_JIGSAW_ID:
			return new GuiJigsaw((TileEntityJigsaw) world.getTileEntity(new BlockPos(x, y, z)));
		case Reference.GUI_BIOME_COMPASS:
			return new GuiBiomeCompass(getCompassBiome(player.getHeldItemMainhand()));
		case Reference.GUI_UPGRADING_STATION:
			return new GuiUpgradingStation(player, (TileEntityUpgradingStation) world.getTileEntity(new BlockPos(x, y, z)));
		default:
			throw new IllegalArgumentException("Unexpected value: " + ID);
		}
	}

	@SuppressWarnings("unused")
	private Container EntityContains(EntityPlayer player, World world, int entityId) {
		Entity entity = world.getEntityByID(entityId);
		if (entity instanceof EntityChaosSage && !entity.isDead)
        {
			System.out.println("CONTAINER(entityID) : " + entityId);
			return emptyContainer();
        }
		return null;
	}
	
	private Container emptyContainer() {
		return new Container() {
			@Override
			public boolean canInteractWith(EntityPlayer playerIn) {
				return true;
			}
		};
	}
	
	/**
	 * Returns id of previously searched biome
	 * @param stack
	 * @return
	 */
	@Nullable
	private String getCompassBiome(ItemStack stack) {
		if (!stack.isEmpty() && stack.getItem() instanceof ItemBiomeCompass) {
			NBTTagCompound tag = stack.getOrCreateSubCompound("data");
			if (tag.hasKey(ItemBiomeCompass.SEARCHED_BIOME)) {
				return tag.getString(ItemBiomeCompass.SEARCHED_BIOME);
			}
		}

		return null;
	}
}