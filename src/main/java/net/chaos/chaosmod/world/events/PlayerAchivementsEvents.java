package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class PlayerAchivementsEvents {
	
	@SubscribeEvent
	public void onAdvancementMade(AdvancementEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = player.getEntityWorld();
		int count = 2;
		System.out.println(event.getAdvancement().getId().toString());
		
		if (event.getAdvancement().getId().toString().equals("chaosmod:harvest_reward") && !world.isRemote) {
			if (player.inventory.addItemStackToInventory(new ItemStack(ModItems.OXONIUM_INGOT, count))) {
				System.out.println("Gifted with 2 oxonium_ingot");
			} else {
				EntityItem entity = new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(ModItems.OXONIUM_INGOT, count));
				if (!world.isRemote && world.spawnEntity(entity)) {
				}
			}
		}
	}

}
