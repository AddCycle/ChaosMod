package net.chaos.chaosmod.network;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.jobs.CapabilityPlayerJobs;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncPlayerJobs implements IMessage {
    private NBTTagCompound data;

    public PacketSyncPlayerJobs() {}

    public PacketSyncPlayerJobs(PlayerJobs jobs) {
        this.data = jobs.serializeNBT();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        data = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, data);
    }

    public static class Handler implements IMessageHandler<PacketSyncPlayerJobs, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncPlayerJobs message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayerSP player = Minecraft.getMinecraft().player;
                player.getCapability(CapabilityPlayerJobs.PLAYER_JOBS, null)
                      .deserializeNBT(message.data);
            });
            return null;
        }
    }
}