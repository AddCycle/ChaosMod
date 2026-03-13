package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.client.gui.inventory.OxoniumFurnaceGui;
import net.chaos.chaosmod.tileentity.TileEntityOxoniumFurnace;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
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
			openOxoniumFurnaceGui();
			return null;
		}
	}

	@SideOnly(Side.CLIENT)
	public static void openOxoniumFurnaceGui() {
		Minecraft mc = Minecraft.getMinecraft();
		TileEntity tile = mc.world.getTileEntity(mc.player.getPosition().add(1, 0, 0));
		if (tile instanceof TileEntityOxoniumFurnace) {
			mc.displayGuiScreen(new OxoniumFurnaceGui(mc.player.inventory, (TileEntityOxoniumFurnace) tile));
		}
	}
}