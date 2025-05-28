package net.chaos.chaosmod.world.events;

import java.util.UUID;

import net.chaos.chaosmod.init.ModItems;
import net.chaos.chaosmod.items.AbstractCustomBow;
import net.chaos.chaosmod.items.special.OxoniumBow;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@EventBusSubscriber
public class PlayerFightEvents {
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

	    /*ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
	    if (!helmet.isEmpty() && helmet.getItem() == ModItems.OXONIUM_HELMET) {
	    	if (event.getSource() == DamageSource.ROCK)
	    }*/
	}
	
	// To disable the cooldown like the pre-1.9 mc ver
	@SubscribeEvent
	public void onSwing(PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END && !event.player.world.isRemote) {
	        event.player.resetCooldown(); // Disables the cooldown — resets it every tick
	    }
	}
	
	// To apply all the modifiers
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
	    EntityPlayer player = event.getEntityPlayer();

	    // Only modify for main-hand melee attacks
	    if (player != null && !player.world.isRemote && event.getTarget() instanceof EntityLivingBase) {
	        ItemStack stack = player.getHeldItemMainhand();
	        if (stack.getItem() instanceof ItemSword || stack.getItem() instanceof ItemAxe) {
	            EntityLivingBase target = (EntityLivingBase) event.getTarget();

	            // Cancel default attack behavior
	            event.setCanceled(true);

	            // Manually apply full attack damage
	            float damage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();

	            // Optionally apply enchantments
	            damage += EnchantmentHelper.getModifierForCreature(stack, target.getCreatureAttribute());

	            // Deal full damage
	            target.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);

	            // Play attack animation
	            player.resetCooldown();
	            player.swingArm(EnumHand.MAIN_HAND);
	        }
	    }
	}
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
	    if (player.world.isRemote || event.phase == TickEvent.Phase.END) return;

	    ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
	    ItemStack held = player.getHeldItemMainhand();
	    if (!held.isEmpty() && held.getItem() == ModItems.ALL_IN_ONE_BOW) {
	    	player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 20));
	    }
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
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onFOVUpdate(FOVUpdateEvent event) {
	    EntityPlayer player = event.getEntity();

	    Item item = player.getActiveItemStack().getItem();
	    if (item instanceof AbstractCustomBow) {
	        ItemStack stack = player.getActiveItemStack();

	        int maxDuration = stack.getMaxItemUseDuration();
	        int useCount = player.getItemInUseCount();
	        int ticksUsed = maxDuration - useCount;

	        // Vanilla uses 20.0F — you use 5.0F for full draw
	        float drawSpeed = ((AbstractCustomBow) item).drawTime;

	        float draw = (float) ticksUsed / drawSpeed;
	        draw = Math.min(draw, 1.0F);

	        // Same formula as vanilla, just scaled faster
	        event.setNewfov(event.getNewfov() * (1.0F - draw * 0.15F));
	    } else if (item instanceof OxoniumBow) {
	        ItemStack stack = player.getActiveItemStack();

	        int maxDuration = stack.getMaxItemUseDuration();
	        int useCount = player.getItemInUseCount();
	        int ticksUsed = maxDuration - useCount;

	        // Vanilla uses 20.0F — you use 5.0F for full draw
	        float drawSpeed = 5.0f;

	        float draw = (float) ticksUsed / drawSpeed;
	        draw = Math.min(draw, 1.0F);

	        // Same formula as vanilla, just scaled faster
	        event.setNewfov(event.getNewfov() * (1.0F - draw * 0.15F));
	    }
	}
}