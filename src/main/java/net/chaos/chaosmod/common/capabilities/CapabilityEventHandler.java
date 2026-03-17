package net.chaos.chaosmod.common.capabilities;

import java.util.function.BiConsumer;

import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.common.capabilities.biome.CapabilityVisitedBiomes;
import net.chaos.chaosmod.common.capabilities.biome.VisitedBiomesProvider;
import net.chaos.chaosmod.common.capabilities.money.IMoney;
import net.chaos.chaosmod.common.capabilities.money.MoneyProvider;
import net.chaos.chaosmod.common.capabilities.money.MoneyStorage;
import net.chaos.chaosmod.jobs.CapabilityPlayerJobs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class CapabilityEventHandler {
	private static final ResourceLocation MONEY_CAPABILITY_ID = new ResourceLocation(Reference.MODID, "money");
	public static final ResourceLocation VISITED_BIOMES_CAPABILITY_ID = new ResourceLocation(Reference.MODID, "visited_biomes");

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (!(event.getObject() instanceof EntityPlayer)) return;

		Main.getLogger().debug("ATTACHING " + Reference.MODID + " capabilities...");

	    attachCapabilities(event);
	}

	@SubscribeEvent
	public static void clonePlayer(PlayerEvent.Clone event) {
		if (!event.isWasDeath()) return;

		Main.getLogger().debug("syncing capabilities reason => [onDeath]");
		syncCapability(MoneyProvider.MONEY_CAPABILITY, event, (oldMoney, newMoney) -> {
			newMoney.set(oldMoney.get());
		});

		syncCapability(CapabilityVisitedBiomes.VISITED_BIOMES, event, (oldCap, newCap) -> {
			newCap.copyFrom(oldCap);
		});
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
		IMoney money = event.player.getCapability(MoneyProvider.MONEY_CAPABILITY, null);
		Main.getLogger().info("MONEY capability default value : {}", money.get());
	}
	
	public static void registerAllCapabilities(FMLPreInitializationEvent event) {
		MoneyStorage.register();
		CapabilityPlayerJobs.register();
		CapabilityVisitedBiomes.register();
	}

	private static void attachCapabilities(AttachCapabilitiesEvent<Entity> event) {
	    event.addCapability(MONEY_CAPABILITY_ID, new MoneyProvider());
	    event.addCapability(VISITED_BIOMES_CAPABILITY_ID, new VisitedBiomesProvider());
	}
	
	private static <T> void syncCapability(Capability<T> capability, PlayerEvent.Clone event, BiConsumer<T, T> callback) {
		T oldOne = event.getOriginal().getCapability(capability, null);
		T newOne = event.getEntityPlayer().getCapability(capability, null);
		if (oldOne != null && newOne != null) {
			callback.accept(oldOne, newOne);
		}
	}
}