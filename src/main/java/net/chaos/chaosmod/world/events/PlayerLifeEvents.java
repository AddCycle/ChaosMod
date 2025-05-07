package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.init.ModBlocks;
import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.items.armor.OxoniumBoots;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.handlers.LightEntityManager;

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
		
	    /*ItemStack held = player.getHeldItemMainhand();

	    if (held.getItem() == Item.getItemFromBlock(ModBlocks.LANTERN)) {
	        // Spawn or update invisible light entity at player’s position
	        LightEntityManager.updateLightEntity(player);
	    } else {
	        // Remove light entity
	        LightEntityManager.removeLightEntity();
	    }*/
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
	    if (event.phase != TickEvent.Phase.END) return;

	    Minecraft mc = Minecraft.getMinecraft();
	    EntityPlayer player = mc.player;

	    if (player == null) return;

	    ItemStack held = player.getHeldItemMainhand();

	    if (!held.isEmpty() && held.getItem() == Item.getItemFromBlock(ModBlocks.LANTERN)) {
	        LightEntityManager.updateLightEntity(player);
	    } else {
	        LightEntityManager.removeLightEntity();
	    }
	}

}
