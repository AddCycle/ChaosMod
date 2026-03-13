package net.chaos.chaosmod.network.market;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.market.MarketClientCache;
import net.chaos.chaosmod.market.MarketData;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class PacketSyncMarket implements IMessage {
	private NBTTagCompound data;
	
	public PacketSyncMarket() {}
	
	public PacketSyncMarket(MarketData marketData) {
		this.data = marketData.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, data);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		data = ByteBufUtils.readTag(buf);
	}
	
	public static class Handler implements IMessageHandler<PacketSyncMarket, IMessage> {

		@Override
		public IMessage onMessage(PacketSyncMarket message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				MarketData clientData = new MarketData();
				clientData.readFromNBT(message.data);
				
				MarketClientCache.updateOffers(clientData.getOffers());
			});

			return null;
		}
		
	}
}