package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.items.special.OxoniumBow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class FightEvents {
	
	@SubscribeEvent
	public void onPlayerHurt(LivingDamageEvent event) {
		if (!(event.getEntityLiving() instanceof EntityPlayer)) return;
		
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		boolean hasCustomBow = false;
		for (ItemStack stack : player.inventory.mainInventory) {
			if (stack.getItem() instanceof OxoniumBow) {
				hasCustomBow = true;
			}
		}
		
		if (hasCustomBow) {
			// for the oxonium the moment
			float increasedDamage = 1.25f * (event.getAmount() - (player.getTotalArmorValue() / 20)); // 20 vanilla armor cap
			event.setAmount(increasedDamage);
			System.out.println("EVENT BOW : increasing damage taken by 25 %");
		}
	}

}
