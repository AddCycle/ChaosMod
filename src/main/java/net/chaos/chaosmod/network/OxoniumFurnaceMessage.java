package net.chaos.chaosmod.network;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.client.gui.inventory.OxoniumFurnaceGui;
import net.chaos.chaosmod.gui.GuideGui;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class OxoniumFurnaceMessage implements IMessage {
	private int toSend;

	public OxoniumFurnaceMessage() {}

	@Override
	public void fromBytes(ByteBuf buf) {
		toSend = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(toSend);
	}
	
	public static class OxoniumFurnaceMessageHandler implements IMessageHandler<OxoniumFurnaceMessage, IMessage> {

		@Override
		public IMessage onMessage(OxoniumFurnaceMessage message, MessageContext ctx) {
			ALZ.alz1();
			return null;
		}
		
	}

}
