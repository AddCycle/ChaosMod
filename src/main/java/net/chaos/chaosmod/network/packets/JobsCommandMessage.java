package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.jobs.gui.GuiScreenJobs;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class JobsCommandMessage implements IMessage {

	public JobsCommandMessage() {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	public static class JobsMessageHandler implements IMessageHandler<JobsCommandMessage, IMessage> {

		@Override
		public IMessage onMessage(JobsCommandMessage message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(JobsCommandMessage::openJobsGui);
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	public static void openJobsGui() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiScreenJobs());
	}
}