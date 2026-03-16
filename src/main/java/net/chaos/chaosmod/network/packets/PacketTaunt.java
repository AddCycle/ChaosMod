package net.chaos.chaosmod.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

// FIXME : not working rn
//@ModPacket(modid = Reference.MODID, side = Side.SERVER)
public class PacketTaunt implements IMessage {
	/**
	 * tauntx = 0
	 * taunty = 1
	 * tauntz = 2
	 * reset = -1
	 */
	private int type = -1;
	
	public PacketTaunt() {}

	public PacketTaunt(int type) {
		this.type = type;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(type);
	}

	public static class PacketTauntHandler implements IMessageHandler<PacketTaunt, IMessage> {

	    @Override
	    public IMessage onMessage(PacketTaunt message, MessageContext ctx) {

	        EntityPlayerMP player = ctx.getServerHandler().player;

	        player.getEntityData().setInteger("taunt", message.type);
	        
	        PacketManager.network.sendToAllTracking(
	                new PacketTaunt(message.type),
	                player
	            );

	        return null;
	    }
	}
}