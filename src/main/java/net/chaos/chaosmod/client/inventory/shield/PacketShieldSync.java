package net.chaos.chaosmod.client.inventory.shield;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
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

public class PacketShieldSync implements IMessage {
    private int playerID;
    private ItemStack shieldStack;

    public PacketShieldSync() {} // required empty constructor

    public PacketShieldSync(EntityPlayer player, ItemStack shield) {
        this.playerID = player.getEntityId();
        this.shieldStack = shield;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer pb = new PacketBuffer(buf);
        this.playerID = pb.readVarInt();
        try {
			this.shieldStack = pb.readItemStack();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer pb = new PacketBuffer(buf);
        pb.writeVarInt(playerID);
        pb.writeItemStack(shieldStack);
    }

    public static class Handler implements IMessageHandler<PacketShieldSync, IMessage> {
        @Override
        public IMessage onMessage(PacketShieldSync message, MessageContext ctx) {
        	if (ctx.side == Side.CLIENT) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    Entity player = Minecraft.getMinecraft().world.getEntityByID(message.playerID);
                    if (player != null) {
                        ClientAccessoryData.setPlayerShield((EntityPlayer) player, message.shieldStack);
                        System.out.println("[Client] Shield updated: " + message.shieldStack);
                    }
                });
            }
            return null;
        }
    }
}