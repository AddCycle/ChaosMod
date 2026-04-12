package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.gui.GuiFinalCredits;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class PacketLaunchFinalCredits implements IMessage {

	public PacketLaunchFinalCredits() {}

	@Override
	public void fromBytes(ByteBuf buf) {}

	@Override
	public void toBytes(ByteBuf buf) {}

	public static class Handler implements IMessageHandler<PacketLaunchFinalCredits, IMessage> {

		@Override
		public IMessage onMessage(PacketLaunchFinalCredits message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(PacketLaunchFinalCredits::openFinalCreditsGui);
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	public static void openFinalCreditsGui() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiFinalCredits());
	}
}
