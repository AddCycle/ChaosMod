package net.chaos.chaosmod.network;

import java.nio.charset.StandardCharsets;
import java.util.Random;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.client.particles.ClientEffectRender;
import net.chaos.chaosmod.client.particles.CoinParticles;
import net.chaos.chaosmod.client.particles.ColoredSweepParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

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
	
	// Inside PacketHandler.java
	public static class ClientHandler implements IMessageHandler<PacketSpawnCustomParticle, IMessage> {
	    @Override
	    public IMessage onMessage(PacketSpawnCustomParticle msg, MessageContext ctx) {
	        Minecraft.getMinecraft().addScheduledTask(() -> {
//	            World world = Minecraft.getMinecraft().world;
//	            EntityPlayer player = Minecraft.getMinecraft().player;
//
//	            if (world != null && player != null) {
//	                Random rand = new Random();
//
//	                double dx = -MathHelper.sin(player.rotationYaw * 0.017453292F);
//	                double dz = MathHelper.cos(player.rotationYaw * 0.017453292F);
//	                double px = msg.x + dx;
//	                double py = msg.y + player.height * 0.5;
//	                double pz = msg.z + dz;
//
//	                if (msg.type.equals("sweep")) {
//	                    Minecraft.getMinecraft().effectRenderer.addEffect(
//	                        new ColoredSweepParticle(Minecraft.getMinecraft().getTextureManager(), world, px, py, pz, dx, 0.0D, dz, rand.nextInt(0xffffff + 1)));
//	                } else if (msg.type.equals("coin")) {
//	                    Minecraft.getMinecraft().effectRenderer.addEffect(
//	                        new CoinParticles(Minecraft.getMinecraft().getTextureManager(), world, px, py, pz, dx, 0.0D, dz));
//	                }
//	            }
	            Minecraft.getMinecraft().addScheduledTask(() -> {
	                ClientEffectRender.spawnCustomParticle(msg.type, msg.x, msg.y, msg.z);
	            });
	            return null;
	        });
	        return null;
	    }
	}

}
