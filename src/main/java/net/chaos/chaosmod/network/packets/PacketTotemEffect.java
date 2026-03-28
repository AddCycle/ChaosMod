package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.client.util.ClientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class PacketTotemEffect implements IMessage {
	private ItemStack stack;
	
	public PacketTotemEffect() {}
	
	public PacketTotemEffect(Item item) {
		this.stack = new ItemStack(item);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeItemStack(buf, stack);
	}

	public static class Handler implements IMessageHandler<PacketTotemEffect, IMessage> {

		@Override
		public IMessage onMessage(PacketTotemEffect message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
		        ClientHelper.playTotemEffect(message.stack);
		    });
			return null;
		}
	}
}