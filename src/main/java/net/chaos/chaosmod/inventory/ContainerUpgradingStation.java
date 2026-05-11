package net.chaos.chaosmod.inventory;

import net.chaos.chaosmod.inventory.slots.SlotStationMaterial;
import net.chaos.chaosmod.inventory.slots.SlotUpgradingOutput;
import net.chaos.chaosmod.items.upgrading.IUpgradingRecipe;
import net.chaos.chaosmod.items.upgrading.UpgradingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerUpgradingStation extends Container {
	private InventoryUpgrading upgradingInventory = new InventoryUpgrading(this);
	private InventoryUpgradingResult upgradingResult = new InventoryUpgradingResult();
	private IInventory playerInventory;
	private IInventory tileStation;
	private final EntityPlayer player;
    private final World world;
    /** Position of the station */
    private final BlockPos pos;
	
	public ContainerUpgradingStation(InventoryPlayer playerInventory, IInventory stationInventory, World world, BlockPos pos) {
		this.world = world;
		this.pos = pos;
		this.player = playerInventory.player;
		this.playerInventory = playerInventory;
		this.tileStation = stationInventory;

        this.addSlotToContainer(new SlotUpgradingOutput(player, upgradingInventory, upgradingResult, 0, 134, 35)); // result
        this.addSlotToContainer(new Slot(upgradingInventory, 0, 26, 35)); // input1
        this.addSlotToContainer(new SlotStationMaterial(upgradingInventory, 1, 75, 35)); // input2

        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k)
        {
            this.addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
	}
	
	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
        listener.sendAllWindowProperties(this, this.tileStation);
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
        for (int i = 0; i < this.listeners.size(); ++i)
        {
            IContainerListener icontainerlistener = this.listeners.get(i);

//            if (this.cookTime != this.tileFurnace.getField(2))
//            {
//                icontainerlistener.sendWindowProperty(this, 2, this.tileFurnace.getField(2));
//            }
//
//            if (this.furnaceBurnTime != this.tileFurnace.getField(0))
//            {
//                icontainerlistener.sendWindowProperty(this, 0, this.tileFurnace.getField(0));
//            }
//
//            if (this.currentItemBurnTime != this.tileFurnace.getField(1))
//            {
//                icontainerlistener.sendWindowProperty(this, 1, this.tileFurnace.getField(1));
//            }
//
//            if (this.totalCookTime != this.tileFurnace.getField(3))
//            {
//                icontainerlistener.sendWindowProperty(this, 3, this.tileFurnace.getField(3));
//            }
        }

//        this.cookTime = this.tileFurnace.getField(2);
//        this.furnaceBurnTime = this.tileFurnace.getField(0);
//        this.currentItemBurnTime = this.tileFurnace.getField(1);
//        this.totalCookTime = this.tileFurnace.getField(3);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
	/**
	 * Call this to sync with a packet the result of the crafting to the client
	 * @param inventoryIn
	 */
    public void onUpgradingChanged(IInventory inventoryIn)
    {
        this.slotChangedUpgrading(this.world, this.player, this.upgradingInventory, this.upgradingResult);
    }
	
    /**
     * FIXME : syncing issues are coming only from here
     * @param world
     * @param player
     * @param inventory
     * @param inventoryResult
     */
    protected void slotChangedUpgrading(World world, EntityPlayer player, InventoryUpgrading inventory, InventoryUpgradingResult inventoryResult)
    {
        if (!world.isRemote)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)player;
            ItemStack itemstack = ItemStack.EMPTY;
            IUpgradingRecipe irecipe = UpgradingManager.findMatchingRecipe(inventory, world);

            if (irecipe != null)
            {
                itemstack = irecipe.getUpgradingResult(inventory);
            }

            inventoryResult.setInventorySlotContents(0, itemstack);
            entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 0, itemstack));
            entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 1, inventory.getStackInSlot(0)));
            entityplayermp.connection.sendPacket(new SPacketSetSlot(this.windowId, 2, inventory.getStackInSlot(1)));
        }
    }

}