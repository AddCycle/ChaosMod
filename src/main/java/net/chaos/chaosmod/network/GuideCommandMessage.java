package net.chaos.chaosmod.network;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.gui.GuideGui;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
			Minecraft.getMinecraft().displayGuiScreen(new GuideGui(0));
			return null;
		}
		
	}

}
