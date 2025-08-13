package net.chaos.chaosmod.network;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.jobs.JobsManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncJobs implements IMessage {
	public String jsonData;
	
	public PacketSyncJobs() {}
	public PacketSyncJobs(String jsonData) {
		this.jsonData = jsonData;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		jsonData = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, jsonData);
	}
	
	public static class Handler implements IMessageHandler<PacketSyncJobs, IMessage> {

		@Override
		public IMessage onMessage(PacketSyncJobs message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				JobsManager.loadFromJson(message.jsonData);
			});
			return null;
		}
		
	}

}
