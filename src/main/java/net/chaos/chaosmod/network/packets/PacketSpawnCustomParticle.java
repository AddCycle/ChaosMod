package net.chaos.chaosmod.network.packets;

import java.nio.charset.StandardCharsets;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.client.particles.ClientEffectRender;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import util.Reference;
import util.annotations.ModPacket;

@ModPacket(modid = Reference.MODID, side = Side.CLIENT)
public class PacketSpawnCustomParticle implements IMessage{
	public double x, y, z;
	public String type;

	public PacketSpawnCustomParticle() {}
	public PacketSpawnCustomParticle(String type, double x, double y, double z) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readInt();
		byte[] bytes = new byte[len];
		buf.readBytes(bytes);
		this.type = new String(bytes, StandardCharsets.UTF_8);
		this.x = buf.readDouble();
		this.y = buf.readDouble();
		this.z = buf.readDouble();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		byte[] bytes = type.getBytes(StandardCharsets.UTF_8);
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
	}
	
	public static class ClientHandler implements IMessageHandler<PacketSpawnCustomParticle, IMessage> {
	    @Override
	    public IMessage onMessage(PacketSpawnCustomParticle msg, MessageContext ctx) {
	        Minecraft.getMinecraft().addScheduledTask(() -> {
	            Minecraft.getMinecraft().addScheduledTask(() -> {
	                ClientEffectRender.spawnCustomParticle(msg.type, msg.x, msg.y, msg.z);
	            });
	            return null;
	        });
	        return null;
	    }
	}

}
