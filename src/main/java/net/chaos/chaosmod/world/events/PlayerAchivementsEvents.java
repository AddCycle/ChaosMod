package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModItems;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID, value = Side.SERVER)
public class PlayerAchivementsEvents {

	@SubscribeEvent
	public static void onAdvancementMade(AdvancementEvent event) {
		EntityPlayer player = event.getEntityPlayer();
		World world = player.getEntityWorld();
		int count = 4;

		if (event.getAdvancement().getId().toString().equals(Reference.MODID + "basic/harvest_reward")) {

			ItemStack stack = new ItemStack(ModItems.OXONIUM_INGOT, count);

			giftOrDropReward(world, player, stack);

			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_NOTE_BELL,
					SoundCategory.PLAYERS, 1.0f, 1.0f);
		}
	}

	private static void giftOrDropReward(World world, EntityPlayer player, ItemStack stack) {
		if (!player.inventory.addItemStackToInventory(stack)) {
			EntityItem entity = new EntityItem(world, player.posX, player.posY, player.posZ, stack);
			world.spawnEntity(entity);
			Main.getLogger().info("Gifted " + player.getName() + " with {} {}", stack.getCount(), stack.getItem().getRegistryName());
			return;
		}

		Main.getLogger().info("Gifted " + player.getName() + " with {} {} (dropped inventory_full)", stack.getCount(), stack.getItem().getRegistryName());
	}
}