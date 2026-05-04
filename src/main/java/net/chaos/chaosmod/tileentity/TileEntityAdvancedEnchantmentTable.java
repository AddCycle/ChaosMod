package net.chaos.chaosmod.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.item.ItemCompass;
import net.minecraft.tileentity.TileEntityEnchantmentTable;

public class TileEntityAdvancedEnchantmentTable extends TileEntityEnchantmentTable {
	ItemCompass c;

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
		return new ContainerEnchantment(playerInventory, this.world, this.pos) {
			@Override
			public boolean canInteractWith(EntityPlayer playerIn) {
				return true;
			}
		};
	}
}