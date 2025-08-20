package net.chaos.chaosmod.common.capabilities;

import net.chaos.chaosmod.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class CapabilityEventHandler {
	private static final ResourceLocation MONEY_CAPABILITY_ID = new ResourceLocation(Reference.MODID, "money");
	
	@SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer) {
        	Main.getLogger().info("ATTACHING MONEY capability attachCapability event");
        	event.addCapability(MONEY_CAPABILITY_ID, new MoneyProvider());
        	Main.getLogger().info("ATTACHED MONEY capability attachCapability event : {}", event.getCapabilities());
        }
    }

    // Persist money after death (clone player data)
    @SubscribeEvent
    public static void clonePlayer(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
        	Main.getLogger().info("ATTACHING MONEY capability back cloning player");
            IMoney oldMoney = event.getOriginal().getCapability(MoneyProvider.MONEY_CAPABILITY, null);
            IMoney newMoney = event.getEntityPlayer().getCapability(MoneyProvider.MONEY_CAPABILITY, null);
            if (oldMoney != null && newMoney != null) {
                newMoney.set(oldMoney.get());
            }
        }
    }
    
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
        IMoney money = event.player.getCapability(MoneyProvider.MONEY_CAPABILITY, null);
        Main.getLogger().info("MONEY capability default value set : {}", money.get());
    }
}
