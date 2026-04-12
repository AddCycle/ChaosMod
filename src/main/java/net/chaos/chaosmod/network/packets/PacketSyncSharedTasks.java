package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.jobs.data.ClientSharedTaskCache;
import net.chaos.chaosmod.jobs.task.SharedTaskProgress;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class PacketSyncSharedTasks implements IMessage {
	private String progressJson;
	
	public PacketSyncSharedTasks() {}

	public PacketSyncSharedTasks(SharedTaskProgress progress) {
		this.progressJson = progress.toJson();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		progressJson = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, progressJson);
	}
	
	public static class Handler implements IMessageHandler<PacketSyncSharedTasks, IMessage> {

		@Override
		public IMessage onMessage(PacketSyncSharedTasks message, MessageContext ctx) {
			loadCache(message);
			return null;
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void loadCache(PacketSyncSharedTasks message) {
		Minecraft.getMinecraft().addScheduledTask(() -> {
			ClientSharedTaskCache.load(message.progressJson); // client-side cache for rendering
		});
	}
}