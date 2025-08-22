package net.chaos.chaosmod.world.events;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.init.ModDamageSources;
import net.chaos.chaosmod.network.PacketManager;
import net.chaos.chaosmod.network.PacketShowFireOverlay;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerFireCommonHandler {

	@SubscribeEvent
	public static void onPlayerHurt(LivingHurtEvent event) {
		if (event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if (event.getSource() == ModDamageSources.BLUE_FIRE) {
				int seconds = 100;
				int ticks = seconds * 20;
				boolean shouldShow = true;
				PacketManager.network.sendTo(new PacketShowFireOverlay(shouldShow, ticks), (EntityPlayerMP) player);
				Main.getLogger().info("Packet sent with blue fire rendering, {}", event.getSource());
			}
		}
	}
	
	@SubscribeEvent
	public static void onClientTick(TickEvent.PlayerTickEvent event) {
	    if (event.player.getEntityData().getBoolean("ShowCustomFireOverlay")) {
            Main.getLogger().info("Boolean got blue_fire : rendering...");
	        int ticks = event.player.getEntityData().getInteger("CustomFireOverlayTicks");
	        if (ticks > 0) {
	            event.player.getEntityData().setInteger("CustomFireOverlayTicks", ticks - 1);
	        } else {
	            event.player.getEntityData().setBoolean("ShowCustomFireOverlay", false);
	        }
	    }
	}
}
