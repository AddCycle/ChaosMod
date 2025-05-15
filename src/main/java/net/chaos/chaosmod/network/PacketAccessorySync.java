package net.chaos.chaosmod.network;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import net.chaos.chaosmod.Main;
import net.chaos.chaosmod.client.inventory.ClientAccessoryData;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PacketAccessorySync implements IMessage {
    private int playerID;
    private ItemStack necklaceStack;

    public PacketAccessorySync() {} // required empty constructor

    public PacketAccessorySync(EntityPlayer player, ItemStack stack) {
        this.playerID = player.getEntityId();
        this.necklaceStack = stack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer pb = new PacketBuffer(buf);
        this.playerID = pb.readVarInt();
        try {
			this.necklaceStack = pb.readItemStack();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer pb = new PacketBuffer(buf);
        pb.writeVarInt(playerID);
        pb.writeItemStack(necklaceStack);
    }

    public static class Handler implements IMessageHandler<PacketAccessorySync, IMessage> {
        @Override
        public IMessage onMessage(PacketAccessorySync message, MessageContext ctx) {
        	if (ctx.side == Side.CLIENT) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    Entity player = Minecraft.getMinecraft().world.getEntityByID(message.playerID);
                    if (player != null) {
                        ClientAccessoryData.setPlayerNecklace((EntityPlayer) player, message.necklaceStack);
                        System.out.println("[Client] Necklace updated: " + message.necklaceStack);
                    }
                });
            }
            return null;
        }
    }
}