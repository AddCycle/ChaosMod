package net.chaos.chaosmod.inventory.slots;

import net.chaos.chaosmod.inventory.InventoryUpgrading;
import net.chaos.chaosmod.inventory.InventoryUpgradingResult;
import net.chaos.chaosmod.items.upgrading.UpgradingManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class SlotUpgradingOutput extends Slot {
	private final InventoryUpgrading inventory;
	private final EntityPlayer player;

	public SlotUpgradingOutput(EntityPlayer player, InventoryUpgrading upgradingInv, InventoryUpgradingResult inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
		inventory = upgradingInv;
		this.player = player;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}
	
    public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack)
    {
        NonNullList<ItemStack> nonnulllist = UpgradingManager.getRemainingItems(this.inventory, thePlayer.world);
        
        this.inventory.consumeInputs(); // decrease everything without firing any shitty event again

        for (int i = 0; i < nonnulllist.size(); i++)
        {
            ItemStack remainder = nonnulllist.get(i);
            if (!remainder.isEmpty())
            {
                if (!thePlayer.inventory.addItemStackToInventory(remainder))
                {
                    thePlayer.dropItem(remainder, false);
                }
            }
        }
        
        this.inventory.getEventHandler().onUpgradingChanged(this.inventory);

        return stack;
    }
}