package net.chaos.chaosmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GuideCommandMessage implements IMessage {
	private int toSend;

	public GuideCommandMessage() {}

	@Override
	public void fromBytes(ByteBuf buf) {
		toSend = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(toSend);
	}
	
	public static class GuideMessageHandler implements IMessageHandler<GuideCommandMessage, IMessage> {

		@Override
		public IMessage onMessage(GuideCommandMessage message, MessageContext ctx) {
			ALZ.alz0();
			return null;
		}
		
	}

}
