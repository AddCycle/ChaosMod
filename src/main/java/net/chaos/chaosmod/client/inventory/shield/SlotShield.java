package net.chaos.chaosmod.client.inventory.shield;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModCapabilities;
import net.chaos.chaosmod.items.shield.CustomItemShield;
import net.chaos.chaosmod.network.PacketAccessorySync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;

public class SlotShield extends Slot {
	private EntityPlayer player;
	public boolean visible = true;

	public SlotShield(EntityPlayer player, IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.player = player;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return stack.getItem() instanceof CustomItemShield;
    }
    
    @Override
    public ItemStack getStack() {
        IShield cap = player.getCapability(ModCapabilities.SHIELD, null);
        return cap != null ? cap.getShieldItem() : ItemStack.EMPTY;
    }

    @Override
    public void putStack(ItemStack stack) {
    	IShield cap = player.getCapability(ModCapabilities.SHIELD, null);
        if (cap != null) {
            cap.setShieldItem(stack);

            if (!player.world.isRemote) {
            	PacketShieldSync packet = new PacketShieldSync(player, stack);
                Main.network.sendTo(packet, (EntityPlayerMP) player);
                
                Main.network.sendToAllTracking(packet, player);
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
