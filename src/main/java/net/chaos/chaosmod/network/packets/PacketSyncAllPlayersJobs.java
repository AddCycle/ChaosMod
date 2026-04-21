package net.chaos.chaosmod.network.packets;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.jobs.PlayerJobs;
import net.chaos.chaosmod.jobs.data.ClientJobsCache;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class PacketSyncAllPlayersJobs implements IMessage {

	private Map<UUID, NBTTagCompound> data = new HashMap<>();
	private Map<UUID, String> names = new HashMap<>();

    public PacketSyncAllPlayersJobs() {}

    // Called server-side to build the packet
    public PacketSyncAllPlayersJobs(Map<UUID, NBTTagCompound> jobsData, Map<UUID, String> names) {
        this.data.putAll(jobsData);
        this.names.putAll(names);
    }
    public PacketSyncAllPlayersJobs(Map<UUID, PlayerJobs> playerJobsMap) {
        for (Map.Entry<UUID, PlayerJobs> entry : playerJobsMap.entrySet()) {
            data.put(entry.getKey(), entry.getValue().serializeNBT());
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(data.size());
        for (Map.Entry<UUID, NBTTagCompound> entry : data.entrySet()) {
            buf.writeLong(entry.getKey().getMostSignificantBits());
            buf.writeLong(entry.getKey().getLeastSignificantBits());
            ByteBufUtils.writeUTF8String(buf, names.getOrDefault(entry.getKey(), "Unknown"));
            ByteBufUtils.writeTag(buf, entry.getValue());
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            UUID uuid = new UUID(buf.readLong(), buf.readLong());
            String name = ByteBufUtils.readUTF8String(buf); // must match write order
            NBTTagCompound nbt = ByteBufUtils.readTag(buf);
            names.put(uuid, name);
            data.put(uuid, nbt);
        }
    }

    public Map<UUID, NBTTagCompound> getData() {
        return data;
    }
    
    public Map<UUID, String> getNames() {
    	return names;
    }

    public static class Handler implements IMessageHandler<PacketSyncAllPlayersJobs, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncAllPlayersJobs message, MessageContext ctx) {
        	Minecraft.getMinecraft().addScheduledTask(() -> {
                ClientJobsCache.INSTANCE.update(message.getData(), message.getNames());
            });
            return null;
        }
    }
}