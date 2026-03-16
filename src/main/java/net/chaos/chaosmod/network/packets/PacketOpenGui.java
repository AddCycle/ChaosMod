package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.Main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.SERVER)
public class PacketOpenGui implements IMessage {
	private int id;
	
	public PacketOpenGui() {}

	public PacketOpenGui(int id) {
		this.id = id;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.id = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.id);
	}

	public static class Handler implements IMessageHandler<PacketOpenGui, IMessage> {

		@Override
		public IMessage onMessage(PacketOpenGui message, MessageContext ctx) {
			EntityPlayerMP player = ctx.getServerHandler().player;

			player.getServerWorld().addScheduledTask(() ->
			{
				player.openGui(Main.instance, message.id, player.world, 0, 0, 0);
			});
			return null;
		}
	}

}
