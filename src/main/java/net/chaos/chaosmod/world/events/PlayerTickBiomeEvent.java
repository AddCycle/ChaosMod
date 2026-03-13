package net.chaos.chaosmod.world.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.chaos.chaosmod.network.packets.PacketManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import util.Reference;
import util.broadcast.MessageDisplayText;

@EventBusSubscriber(modid = Reference.MODID)
/**
 * TODO: store the visited biomes into the player with a capability
 */
public class PlayerTickBiomeEvent {
	private static final Map<UUID, List<Biome>> lastBiomes = new HashMap<>();
	private static String currentBiomeName;

	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.END)
			return;

		EntityPlayer player = event.player;
		World world = player.getEntityWorld();
		BlockPos pos = player.getPosition();
		Biome currentBiome = world.getBiome(pos);
		UUID playerId = player.getUniqueID();

		if (world.isRemote) {
			setCurrentBiomeName(currentBiome.getBiomeName());
			return;
		}

		// SERVER: biome tracking
		List<Biome> visitedBiomes = lastBiomes.get(playerId);
		if (visitedBiomes == null) {
			visitedBiomes = new ArrayList<>();
		}

		if (!visitedBiomes.contains(currentBiome)) {
			visitedBiomes.add(currentBiome);

			String dataToSend;

			boolean isSinglePlayer = FMLCommonHandler.instance().getMinecraftServerInstance().isSinglePlayer();

			if (isSinglePlayer) {
				String displayName = currentBiome.getBiomeName();
				dataToSend = "NAME:" + displayName;
			} else {
				String registryName = currentBiome.getRegistryName().toString();
				dataToSend = "REG:" + registryName;
			}

			PacketManager.network.sendTo(new MessageDisplayText(dataToSend), (EntityPlayerMP) player);

			world.playSound(null, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS,
					1.0f, 1.0f);
		}

		lastBiomes.put(playerId, visitedBiomes);
	}

	public static String getCurrentBiomeName() {
		return currentBiomeName;
	}

	public static void setCurrentBiomeName(String newBiomeName) {
		currentBiomeName = newBiomeName;
	}
}
