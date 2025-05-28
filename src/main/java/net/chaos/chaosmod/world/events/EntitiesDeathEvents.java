package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntitiesDeathEvents {

	@SubscribeEvent
	/*
	 * This event is intended
	 */
	public void onEntityDeath(LivingDeathEvent event) {
		if (!(event.getEntity() instanceof EntityDragon)) return;
		
		World world = event.getEntity().world;
        if (world.isRemote) return; // only run on server

        EntityDragon dragon = (EntityDragon) event.getEntity();
        if (!dragon.isNonBoss()) return;

        ItemStack loot = new ItemStack(ModItems.CHAOS_HEART);
        EntityItem drop = new EntityItem(world, dragon.posX, dragon.posY, dragon.posZ, loot);
        world.spawnEntity(drop);
	}
	
}
