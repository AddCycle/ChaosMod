package net.chaos.chaosmod.world.events;

import java.util.Iterator;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.items.AbstractItemPouch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerGatheringEvents {
	
	@SubscribeEvent
	// INFO : EventBusSubscriber requires static methods
	public static void onBlockHarvest(HarvestDropsEvent event) {
		EntityPlayer player = event.getHarvester();
		if (player == null) return;
		
		for (ItemStack invStack : player.inventory.mainInventory) {
	        if (invStack.getItem() == ModItems.COBBLESTONE_VOID) {
	            Iterator<ItemStack> iter = event.getDrops().iterator();
	            while (iter.hasNext()) {
	                ItemStack drop = iter.next();
	                if (drop.getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)) {
	                    ((AbstractItemPouch) invStack.getItem()).storeBlock(invStack, drop.getItem());
	                    iter.remove();
	                }
	            }
	        }
	    }
	}

}
