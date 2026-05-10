package net.chaos.chaosmod.inventory;

import net.chaos.chaosmod.inventory.slots.SlotOutput;
import net.chaos.chaosmod.inventory.slots.SlotStationMaterial;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerUpgradingStation extends Container {
	private IInventory playerInventory;
	private IInventory tileStation;
	
	public ContainerUpgradingStation(InventoryPlayer playerInventory, IInventory stationInventory) {
		this.playerInventory = playerInventory;
		this.tileStation = stationInventory;

        this.addSlotToContainer(new Slot(stationInventory, 0, 26, 35)); // input1
        this.addSlotToContainer(new SlotStationMaterial(stationInventory, 1, 75, 35)); // input2
        this.addSlotToContainer(new SlotOutput(stationInventory, 2, 134, 35));

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
}