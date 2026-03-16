package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.cutscene.CutsceneManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

// TODO : later add an id for the cutscene or type or write multiple packets
@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class PacketCutscene implements IMessage {
	private BlockPos pos;

	public PacketCutscene() {}

	public PacketCutscene(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
	}

	public static class Handler implements IMessageHandler<PacketCutscene, IMessage> {

		@Override
		public IMessage onMessage(PacketCutscene message, MessageContext ctx) {

			Minecraft.getMinecraft().addScheduledTask(() -> {
				CutsceneManager.startCutscene(message.pos);
			});
			return null;
		}
	}

}