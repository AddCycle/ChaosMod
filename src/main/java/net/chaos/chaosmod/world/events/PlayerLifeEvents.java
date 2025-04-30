package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.items.armor.OxoniumBoots;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class PlayerLifeEvents {
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
		
		if (!boots.isEmpty() && boots.getItem() instanceof OxoniumBoots) {
			player.stepHeight = 1.0f;
		} else {
			player.stepHeight = 0.6f;
		}
	}

}
