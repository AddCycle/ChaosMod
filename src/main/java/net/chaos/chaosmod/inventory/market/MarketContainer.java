package net.chaos.chaosmod.inventory.market;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class MarketContainer extends Container {
	
	public MarketContainer() {}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}

}
