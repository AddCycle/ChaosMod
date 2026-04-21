package net.chaos.chaosmod.jobs.events;

import net.chaos.chaosmod.jobs.data.WorldSavedDataJobs;
import net.chaos.chaosmod.network.packets.PacketManager;
import net.chaos.chaosmod.network.packets.PacketSyncAllPlayersJobs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import util.Reference;

@EventBusSubscriber(modid = Reference.MODID)
public class PlayerJobsSyncEvents {

	@SubscribeEvent
    public static void onPlayerLogin(PlayerLoggedInEvent event) {
        if (!(event.player instanceof EntityPlayerMP)) return;
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        MinecraftServer server = player.getServer();

        server.addScheduledTask(() -> {
            // Persist this player's latest data
            WorldSavedDataJobs worldData = WorldSavedDataJobs.get(server.getEntityWorld());
            worldData.updatePlayer(player);

            // Push full historical data to all clients
            syncAllClients(server, worldData);
        });
    }

    @SubscribeEvent
    public static void onPlayerLogout(PlayerLoggedOutEvent event) {
        if (!(event.player instanceof EntityPlayerMP)) return;
        EntityPlayerMP player = (EntityPlayerMP) event.player;
        MinecraftServer server = player.getServer();

        server.addScheduledTask(() -> {
            // Save their final state before they disappear from the player list
            WorldSavedDataJobs worldData = WorldSavedDataJobs.get(server.getEntityWorld());
            worldData.updatePlayer(player);

            syncAllClients(server, worldData);
        });
    }

    // Also call this when a task is completed to keep data fresh
    public static void onJobProgressChanged(EntityPlayerMP player) {
        MinecraftServer server = player.getServer();
        WorldSavedDataJobs worldData = WorldSavedDataJobs.get(server.getEntityWorld());
        worldData.updatePlayer(player);
        syncAllClients(server, worldData);
    }

    private static void syncAllClients(MinecraftServer server, WorldSavedDataJobs worldData) {
        PacketSyncAllPlayersJobs packet = new PacketSyncAllPlayersJobs(
            worldData.getJobsData(),
            worldData.getNames()
        );
        PacketManager.network.sendToAll(packet);
    }
}