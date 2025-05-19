package net.chaos.chaosmod.client.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;

public class ContainerAccessory extends ContainerPlayer {
	public final AccessoryInventory accessory1;
	public final AccessoryInventory accessory2;
	public final AccessoryInventory accessory3;

    public ContainerAccessory(InventoryPlayer playerInventory, boolean localWorld, EntityPlayer player) {
        super(playerInventory, localWorld, player);
        this.accessory1 = new AccessoryInventory(player);
        this.accessory2 = new AccessoryInventory(player);
        this.accessory3 = new AccessoryInventory(player);

        // Add custom slot to the container (screen X=80, Y=8 for example)
        int offset = 4;
        this.addSlotToContainer(new SlotAccessory(player, accessory1, 0, offset, -20));
        this.addSlotToContainer(new SlotAccessory(player, accessory2, 0, offset + 20, -20));
        this.addSlotToContainer(new SlotAccessory(player, accessory3, 0, offset + 20 * 2, -20));
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}
