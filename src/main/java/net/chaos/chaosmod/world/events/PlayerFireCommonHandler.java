package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModDamageSources;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketShowFireOverlay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerFireCommonHandler {

	@SubscribeEvent
	public static void onPlayerHurt(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (event.getSource() == ModDamageSources.BLUE_FIRE) {

				int seconds = 10;
				int ticks = seconds * 20;

				player.getEntityData().setInteger("CustomFireOverlayTicks", ticks); // server

				boolean shouldShow = true;
				PacketManager.network.sendTo(new PacketShowFireOverlay(shouldShow, ticks), (EntityPlayerMP) player);
				Main.getLogger().info("Packet sent with blue fire rendering, {}", event.getSource());
			}
		}
	}
	
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != Phase.END) return;
		
		EntityPlayer player = event.player;

		if (!player.world.isRemote) {
			int ticks = player.getEntityData().getInteger("CustomFireOverlayTicks");

	        if (ticks > 0 && !player.isInWater()) {
	            if (ticks % 20 == 0) {
	                player.attackEntityFrom(ModDamageSources.BLUE_FIRE_DAMAGE, 1.0F);
	                player.world.playSound(null, player.getPosition(),
	                		SoundEvents.BLOCK_NOTE_BELL, SoundCategory.PLAYERS, 1.0f, 1.0f);
	            }
	            player.getEntityData().setInteger("CustomFireOverlayTicks", ticks - 1);
	        } else if (ticks <= 0 || player.isInWater()) {
	            // stop overlay
	            player.getEntityData().setInteger("CustomFireOverlayTicks", 0);
	            PacketManager.network.sendTo(new PacketShowFireOverlay(false, 0), (EntityPlayerMP) player);
	        }
		}
	}
}