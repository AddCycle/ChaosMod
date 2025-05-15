package net.chaos.chaosmod.network;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.Main;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketOpenAccessoryGui implements IMessage {

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub

	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub

	}
	
	public static class Handler implements IMessageHandler<PacketOpenAccessoryGui, IMessage> {
        @Override
        public IMessage onMessage(PacketOpenAccessoryGui message, MessageContext ctx) {
            EntityPlayerMP player = ctx.getServerHandler().player;
            if (!player.isCreative()) {
            	player.getServerWorld().addScheduledTask(() -> {
            		player.openGui(Main.instance, 6, player.world, 0, 0, 0); // GUI ID = 6
            	});
            }
            return null;
        }
    }

}
