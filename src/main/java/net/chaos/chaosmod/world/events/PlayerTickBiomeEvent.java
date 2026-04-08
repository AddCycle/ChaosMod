package net.chaos.chaosmod.world.events;

import java.util.Set;

import net.chaos.chaosmod.common.capabilities.biome.CapabilityVisitedBiomes;
import net.chaos.chaosmod.common.capabilities.biome.VisitedBiomes;
import net.chaos.chaosmod.jobs.events.traveler.JobTravelerEventHandler;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import util.Reference;
import util.broadcast.MessageDisplayBiomeName;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerTickBiomeEvent {

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase != TickEvent.Phase.END || event.side.isClient())
			return;

		EntityPlayer player = event.player;
		World world = player.getEntityWorld();
		BlockPos pos = player.getPosition();
		Biome currentBiome = world.getBiome(pos);

		VisitedBiomes biomes = player.getCapability(CapabilityVisitedBiomes.VISITED_BIOMES, null);
		if (biomes == null) return;

		Set<Biome> visitedBiomes = biomes.getVisitedBiomes();

		if (visitedBiomes.contains(currentBiome))
			return;

		// FIXME : not working
		if (!JobTravelerEventHandler.onPlayerDiscoversBiome(player, currentBiome)) return;

		String data = currentBiome.getRegistryName().toString();

		PacketManager.network.sendTo(new MessageDisplayBiomeName(data), (EntityPlayerMP) player);

		world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS,
				1.0f, 1.0f);
		
		biomes.addBiome(currentBiome);
	}
}