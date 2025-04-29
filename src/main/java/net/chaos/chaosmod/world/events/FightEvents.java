package net.chaos.chaosmod.world.events;

import java.util.UUID;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.items.special.OxoniumBow;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

@EventBusSubscriber
public class FightEvents {
	public static final UUID SPEED_BOOST_UUID = UUID.fromString("f84c6a4d-2b1d-4315-b6fa-3db1a3d8e5c3");
	public static final UUID ATTACK_BOOST_UUID = UUID.fromString("0e6a8b8a-91d0-4b92-8aa4-7d44a6f5c3b1");
	
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
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
	    if (player.world.isRemote || event.phase != TickEvent.Phase.END) return;

	    ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
	    if (!boots.isEmpty() && boots.getItem() == ModItems.OXONIUM_BOOTS) {
	        IAttributeInstance speedAttribute = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
	        AttributeModifier speedModifier = new AttributeModifier(
	            SPEED_BOOST_UUID,
	            "speed_boost",
	            0.20,
	            1
	        );
	        IAttributeInstance attackAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	        AttributeModifier attackModifier = new AttributeModifier(
	            ATTACK_BOOST_UUID,
	            "attack_boost",
	            0.10,
	            1
	        );

	    	if ((player.getHealth() / player.getMaxHealth() < 0.2)) {
	    		if (speedAttribute.getModifier(SPEED_BOOST_UUID) == null) speedAttribute.applyModifier(speedModifier);
	            if (attackAttribute.getModifier(ATTACK_BOOST_UUID) == null) attackAttribute.applyModifier(attackModifier);
	    	}
	    } else {
	        IAttributeInstance speedAttribute = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
	        if (speedAttribute.getModifier(SPEED_BOOST_UUID) != null) {
	            speedAttribute.removeModifier(SPEED_BOOST_UUID);
	        }
	        IAttributeInstance attackAttribute = player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
	        if (attackAttribute.getModifier(ATTACK_BOOST_UUID) != null) {
	            attackAttribute.removeModifier(ATTACK_BOOST_UUID);
	        }
	    }
	}
}