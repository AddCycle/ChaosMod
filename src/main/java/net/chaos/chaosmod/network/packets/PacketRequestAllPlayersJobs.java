package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.jobs.data.WorldSavedDataJobs;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.SERVER)
public class PacketRequestAllPlayersJobs implements IMessage {

    public PacketRequestAllPlayersJobs() {}

    @Override
    public void fromBytes(ByteBuf buf) {}

    @Override
    public void toBytes(ByteBuf buf) {}

    public static class Handler implements IMessageHandler<PacketRequestAllPlayersJobs, IMessage> {
        @Override
        public IMessage onMessage(PacketRequestAllPlayersJobs message, MessageContext ctx) {
        	EntityPlayerMP requester = ctx.getServerHandler().player;
            MinecraftServer server = requester.getServer();

            // Serve from persistent store, not just online players
            WorldSavedDataJobs worldData = WorldSavedDataJobs.get(server.getEntityWorld());

            PacketManager.network.sendTo(
                new PacketSyncAllPlayersJobs(worldData.getJobsData(), worldData.getNames()),
                requester
            );
            return null;
        }
    }
}