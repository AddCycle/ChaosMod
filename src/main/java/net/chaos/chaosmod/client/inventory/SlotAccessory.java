package net.chaos.chaosmod.client.inventory;

import net.chaos.chaosmod.common.capabilities.accessory.CapabilityAccessory;
import net.chaos.chaosmod.items.necklace.ItemNecklace;
import net.chaos.chaosmod.network.packets.PacketAccessorySync;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotAccessory extends Slot {
	private EntityPlayer player;
	public boolean visible = true;

	public SlotAccessory(EntityPlayer player, IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.player = player;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof ItemNecklace;
    }
    
    @Override
    public ItemStack getStack() {
        IAccessory cap = player.getCapability(CapabilityAccessory.ACCESSORY, null);
        return cap != null ? cap.getAccessoryItem() : ItemStack.EMPTY;
    }

    @Override
    public void putStack(ItemStack stack) {
    	IAccessory cap = player.getCapability(CapabilityAccessory.ACCESSORY, null);
        if (cap != null) {
            cap.setAccessoryItem(stack);

            if (!player.world.isRemote) {
            	PacketAccessorySync packet = new PacketAccessorySync(player, stack);
                PacketManager.network.sendTo(packet, (EntityPlayerMP) player);
                
                PacketManager.network.sendToAllTracking(packet, player);
            }
        }
        this.onSlotChanged();
    }

    @Override
    public boolean isEnabled() {
    	return this.visible;
    }

    @Override
    public boolean canTakeStack(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }

}
