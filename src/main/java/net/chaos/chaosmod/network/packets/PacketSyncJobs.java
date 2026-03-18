package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.jobs.JobsManager;
import net.chaos.chaosmod.jobs.task.JobTaskManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
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
			Main.getLogger().info("PACKET JOBS LOADING SENT...");

			Minecraft.getMinecraft().addScheduledTask(() -> {
				JobsManager.loadFromJson(message.jsonData);
				JobTaskManager.initTasks();
			});

			return null;
		}
		
	}

}
